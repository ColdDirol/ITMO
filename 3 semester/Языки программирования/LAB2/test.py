import subprocess
import unittest

class TestDictionary(unittest.TestCase):
    def run_program(self, stdin):
        program_path = "./main"
        process = subprocess.Popen(
            [program_path],
            stdin=subprocess.PIPE,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            shell=True
        )
        stdout, stderr = process.communicate(input=stdin)
        return stdout.strip(), stderr.strip()

    def run_test(self, values):
        for stdin, expected_stdout, expected_stderr in values:
            with self.subTest(stdin=stdin):
                result_stdout, result_stderr = self.run_program(stdin)
                self.assertEqual(result_stdout, expected_stdout)
                self.assertEqual(result_stderr, expected_stderr)

    def valid_values_test1(self):
        values = [
            ("little", "first", ""),
            ("big", "second", ""),
            ("my", "third", ""),
            ("QUEEN", "fourth", ""),
            ("Tarantino1", "Tarantino2", ""),
            ("Arch", "sixth", ""),
            ("Linux", "seventh", "")
        ]

        self.run_test(values)

    def valid_values_test2(self):
        values = [
            ("little", "first", ""),
            ("big", "second", ""),
            ("my", "third", ""),
            ("QUEEN", "fourth", ""),
            ("Tarantino1", "Tarantino2", "")
        ]

        self.run_test(values)

    def valid_values_test3(self):
        values = [
            ("Tarantino1", "Tarantino2", ""),
            ("Arch", "sixth", ""),
            ("Linux", "seventh", "")
        ]

        self.run_test(values)

    def word_not_found_error_test(self):
        values = [
            ("P3231", "", "Word not found")
        ]
        self.run_test(values)

    def max_length_error_test(self):
        values = [
            ("blablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla", "", "Word is too long. Max avalliable length 256 chars!")
        ]
        self.run_test(values)

if __name__ == "__main__":
    unittest.main()
