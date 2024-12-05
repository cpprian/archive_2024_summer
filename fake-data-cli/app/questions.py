from prompt_toolkit.validation import Validator, ValidationError

class NumberValidator(Validator):
    def validate(self, document):
        try:
            int(document.text)
        except ValueError:
            raise ValidationError(message="Please enter a number",
                                  cursor_position=len(document.text))

class NameValidator(Validator):
    def validate(self, document):
        if len(document.text) == 0:
            raise ValidationError(message="Please enter a valid name",
                                  cursor_position=len(document.text))

start = [
    {
        'type': 'list',
        'name': 'user_option',
        'message': 'Welcome to fake data genertor! Choose where to start:',
        'choices': ["text file", "csv file", "excel sheet", "sql query"]
    }
]

csv_handle = [
    {
        'type': 'list',
        'name': 'csv_option',
        'message': 'Choose csv delimeter:',
        'choices': ["comma", "semicolon", "tab", 'vertical bar']
    }
]

excel = [
    {
        'type': 'input',
        'name': 'excel_sheet_name',
        'message': 'Choose excel sheet name. If you want to use default (DataSheet) name, just press enter:',
        "filter": lambda val: val.strip()
    }
]

sql = [
        {
        'type': 'input',
        'name': 'sql_database',
        'message': 'Choose sql database name:',
        'validate': NameValidator,
        "filter": lambda val: val.strip()
    },
    {
        'type': 'input',
        'name': 'sql_table',
        'message': 'Choose sql table name:',
        'validate': NameValidator,
        "filter": lambda val: val.strip()
    },
    {
        'type': 'input',
        'name': 'sql_column',
        'message': 'Choose sql column names (each separated by comma): (or program will create columns in your table: Firstname, Lastname, Age, City, Street, Zipcode, Country)',
        "filter": lambda val: val.strip()
    },  
]

generate_file = [
    {
        'type': 'input',
        'name': 'file_name',
        'message': 'Enter file name (file will be generated in project\'s bin dir):',
        'validate': NameValidator,
        "filter": lambda val: val.strip()
    },
    {
        'type': 'input',
        'name': 'size',
        'message': 'How many random data do you want to generate?',
        'validate': NumberValidator,
        "filter": lambda val: abs(int(val))
    } 
]