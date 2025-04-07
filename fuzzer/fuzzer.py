import os
import subprocess
import random
import time
from collections import defaultdict

MAX_ITERATIONS = 10000
MAX_TIME = 1000 # seconds
ACTIONS = ['E', 'S', 'U', 'D', 'Q', 'W', 'L', 'R']

def run_jar(jar_path, args=None, timeout=None):
    cmd = ['java', '-jar', jar_path]
    if args:
        cmd.extend(args)

    try:
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            timeout=timeout,
            check=True
        )
        return result.stdout, result.stderr, result.returncode

    except subprocess.TimeoutExpired:
        return '', 'Execution timed out', -1
    except subprocess.CalledProcessError as e:
        return e.stdout, e.stderr, e.returncode
    except Exception as e:
        return '', f'Error: {str(e)}', -1
    
def get_random_actions(max_length):
    n = random.randint(0, max_length)
    return "".join(random.choice(ACTIONS) for _ in range(n))

def generate_random_binary_file(filename, max_size):
    n = random.randint(0, max_size)
    with open(filename, 'wb') as f:
        random_bytes = b''
        for i in range(n // 8):
            random_bytes = random_bytes + random.getrandbits(8).to_bytes()
            
        f.write(random_bytes)

def append_to_log(log_filename, stdout, stderr, code, fuzzed_filename, fuzzed_actions):
    with open(log_filename, 'a') as f:
        f.write('\n' + '=+' * 20 + '\n')
        f.write(f"stdout: {stdout}\n")
        f.write(f"stderr: {stderr}\n")
        f.write(f"return_code: {code}\n")
        f.write(f"fuzzed filename: {fuzzed_filename}")
        f.write(f"fuzzed actions: {fuzzed_actions}\n")
        f.write('=+' * 20 + '\n')

if __name__ == "__main__":
    MAX_FILE_SIZE = 1024     # size in bytes
    MAX_ACTION_SIZE = 10
    FUZZ_FILENAME = "fuzzed.map"
    LOG_FILENAME = "fuzzing.log"
    start_time = time.time()
    return_code_count = defaultdict(int)
    stdout_count = defaultdict(int)

    f = open(LOG_FILENAME, 'w')
    if not os.path.exists("fuzzed_maps_improved"):
        os.mkdir("fuzzed_maps_improved")
    for iteration in range(MAX_ITERATIONS):
        current_time = time.time()
        if current_time - start_time > MAX_TIME:
            print("Time limit reached stopping fuzzing.")
            break
        else:
            print(f"Starting fuzzing iteration {iteration}.")
            print("-" * 20)        
            generate_random_binary_file(FUZZ_FILENAME, MAX_FILE_SIZE)
            fuzzed_actions = get_random_actions(MAX_ACTION_SIZE)
            stdout, stderr, code = run_jar("jpacman-3.0.1.jar", args=[FUZZ_FILENAME, fuzzed_actions], timeout=10)
            return_code_count[code] += 1
            stdout_count[stdout] += 1
            if code != 0:
                fuzzed_filename = f"fuzz_{iteration}.map" 
                os.rename(FUZZ_FILENAME, f"fuzzed_maps_improved/{fuzzed_filename}")
                append_to_log(LOG_FILENAME, stdout, stderr, code, fuzzed_filename, fuzzed_actions)
    f.close()

    print("=" * 20)
    for code, count in return_code_count.items():
        print(f"{count} occurences of {code}.")
    for stdout, count in stdout_count.items():
        print(f"{count} occurences of {stdout}.")
