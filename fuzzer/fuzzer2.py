import os
from pathlib import Path
from random import randint, choice, getrandbits
import string
import subprocess
import time
from collections import defaultdict


class Fuzzer:
    def __init__(self, jar_path):
        self.jar_path = jar_path
        self.jar_timeout = 10

        self.MAX_ACTION_SIZE = 10
        self.MAX_FILE_SIZE = 1024
        self.MAX_ITERATIONS = 100000
        self.MAX_TIME = 600 # seconds
        self.ACTIONS = ['E', 'S', 'U', 'D', 'Q', 'W', 'L', 'R']
        self.CHARS = "000FFFMMMWWWP" # multiple occurrence to reduce P

        self.OUTPUT_DIR = "fuzzed_maps"
        self.Examples = [f"Example_maps/{f}" for f in os.listdir("Example_maps")]
        n = 1
        while os.path.exists(f"{self.OUTPUT_DIR}{"" if n == 1 else n}"):
            n += 1
        self.OUTPUT_DIR = f"{self.OUTPUT_DIR}{"" if n == 1 else n}"
        self.FUZZ_FILENAME = f"fuzzed{n}_%d.map"
        self.LOG_FILENAME = f"fuzzing{n}.log"

        os.makedirs(self.OUTPUT_DIR)


    def run_jar(self, filename, actions):
        cmd = ['java', '-jar', self.jar_path]
        cmd.extend([filename, actions])
        try:
            result = subprocess.run(
                cmd,
                capture_output=True,
                text=True,
                timeout=self.jar_timeout,
                check=True
            )
            return result.stdout, result.stderr, result.returncode
        except subprocess.TimeoutExpired:
            return '', 'Execution timed out', -1
        except subprocess.CalledProcessError as e:
            return e.stdout, e.stderr, e.returncode
        except Exception as e:
            return '', f'Error: {str(e)}', -1

    def append_to_log(self, stdout, stderr, code, fuzzed_filename, fuzzed_actions):
        with open(self.LOG_FILENAME, 'a') as f:
            f.write('\n' + '=+' * 20 + '\n')
            f.write(f"stdout: {stdout}\n")
            f.write(f"stderr: {stderr}\n")
            f.write(f"return_code: {code}\n")
            f.write(f"fuzzed filename: {fuzzed_filename}")
            f.write(f"fuzzed actions: {fuzzed_actions}\n")
            f.write('=+' * 20 + '\n')

    def get_random_actions(self):
        return "".join(choice(self.ACTIONS) for _ in range(randint(0, self.MAX_ACTION_SIZE)))

    def generate_random_binary_file(self, iteration):
        n = randint(0, self.MAX_FILE_SIZE)
        with open(self.FUZZ_FILENAME % iteration) as file:
            random_bytes = b''
            for i in range(n // 8):
                random_bytes = random_bytes + getrandbits(8).to_bytes()

            file.write(random_bytes)

    def generate_improved_file(self, iteration):
        n = randint(1, self.MAX_FILE_SIZE // 20)
        random_content = []
        for _ in range(1,20):
            random_content.append("".join(choice(self.CHARS) for _ in range(n)))
        with open(self.FUZZ_FILENAME % iteration, 'w') as file:
            file.write("\n".join(random_content))

    def generate_mutation_file(self, iteration):
        base_file = self.Examples[randint(0, len(self.Examples) - 1)]
        with open(base_file, 'r') as f:
            content = f.read().split()

        def remove_row():
            if content.__len__() == 0:
                return
            i = randint(0, content.__len__() -1)
            content.pop(i)
        def change_row():
            if content.__len__() == 0 or content[0].__len__() == 0:
                return
            i = randint(0, content.__len__() -1)
            content[i] = ''.join(choice(self.CHARS) for _ in range(content[0].__len__()))
        def add_row():
            if content.__len__() == 0:
                content.append(''.join(choice(self.CHARS) for _ in range(randint(1,20))))
                return
            i = randint(0, content[0].__len__() -1)
            content.insert(i,''.join(choice(self.CHARS) for _ in range(content[0].__len__())))
        def add_column():
            if content.__len__() == 0 or content[0].__len__() == 0:
                return
            i = randint(0, content[0].__len__() - 1)
            for r, row in enumerate(content):
                content[r] += f"{row[0:i]}{choice(self.CHARS)}{row[i:]}"
        def remove_column():
            if content.__len__() == 0 or content[0].__len__() == 0:
                return
            i = randint(0, content[0].__len__() - 1)
            for r, row in enumerate(content):
                content[r] += f"{row[0:i-1]}{row[i:]}"

        mutations = [remove_row, change_row, add_row, remove_column, add_column]

        for _ in range(0, randint(1, 3)):
            mutations[randint(0, len(mutations) - 1)]()

        file_content = '\n'.join(content)
        with open(self.FUZZ_FILENAME % iteration, 'w') as file:
            file.write(file_content)
        file.close()

    def run(self, file_generator):
        start_time = time.time()
        return_code_count = defaultdict(int)
        stdout_count = defaultdict(int)
        for iteration in range(self.MAX_ITERATIONS):
            current_time = time.time()
            if current_time - start_time > self.MAX_TIME:
                print("Time limit reached stopping fuzzing.")
                break

            print(f"Starting fuzzing iteration {iteration}.")
            print("-" * 20)

            file_generator(self, iteration)

            fuzzed_actions = self.get_random_actions()
            filename = self.FUZZ_FILENAME % iteration
            stdout, stderr, code = self.run_jar(filename, fuzzed_actions)
            return_code_count[code] += 1
            stdout_count[stdout] += 1
            if code != 0:
                fuzzed_filename = f"fuzz_{iteration}.map"
                os.rename(self.FUZZ_FILENAME % iteration, f"{self.OUTPUT_DIR}/{fuzzed_filename}")
                self.append_to_log(stdout, stderr, code, fuzzed_filename, fuzzed_actions)
            file = Path(self.FUZZ_FILENAME % iteration)
            if file.exists():
                file.unlink()

        print("=" * 20)
        for code, count in return_code_count.items():
            print(f"{count} occurences of {code}.")
        for stdout, count in stdout_count.items():
            print(f"{count} occurences of {stdout}.")


if __name__ == '__main__':
    f = Fuzzer("jpacman-3.0.1.jar")
    # f.run(Fuzzer.generate_random_binary_file)
    # f.run(Fuzzer.generate_improved_file)
    f.run(Fuzzer.generate_mutation_file)