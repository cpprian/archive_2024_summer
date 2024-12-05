from PyInquirer import prompt
from examples import custom_style_2
import logging as log

from app.questions import start
import app.generate as g

def run():
    answers = prompt(start, style=custom_style_2)
    if answers.get("user_option") == "text file":
        log.info("Generating text file...")
        g.generate_txt()
    elif answers.get("user_option") == "csv file":
        log.info('Generating csv file...')
        g.generate_csv()
    elif answers.get("user_option") == "excel sheet":
        log.info('Generating excel sheet...')
        g.generate_excel()
    elif answers.get("user_option") == "sql query":
        log.info('Generating sql query...')
        g.generate_sql()

if __name__ == "__main__":
    run()