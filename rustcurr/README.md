# rustcurr
A command-line tool written in Rust to converts user amounts between different curriencies using real-time exchange rate data from ExchangeRate-API

## Installation & Configuration
1. Clone the repository
2. Add .env file in the root directory of the project with like in .env.example
   1. Get your API key from [ExchangeRate-API](https://www.exchangerate-api.com/)
   2. Add your API key to the .env file
   3. You can also change cache file path in .env file
3. Run `cargo build --release` in the root directory of the project
4. The binary will be located at `target/release/rustcurr`
5. Run `./rustcurr --help` to see the available commands

## Usage
1. Run `./rustcurr -b <from_currency> -t <from_currency> -a <amount>` to convert an amount from one currency to another
2. Run `./rustcurr -b <from_currency> --list` to list all available currencies and their exchange rates from your specified base currency
3. Run `./rustcurr -i` to run the interactive mode
4. Run `./rustcurr --help` to see the available commands

### Run Docker 
1. Run `docker build -t rustcurr .` in the root directory of the project
2. Run `docker run -it --rm rustcurr` to run the binary
3. In the container type `echo "API_KEY=<your_api_key>
CACHE_FILE_PATH=cache.json" > .env` to create .env file
4. Run `cargo test` to run the tests
5. Run `./target/release/rustcurr --help` to see the available commands
6. As an alternative, you can uncomment the last lines in the Dockerfile to run directly app from /usr/local/bin

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
