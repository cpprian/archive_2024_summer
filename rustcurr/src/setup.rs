use anyhow::{Error, Result};
use rustcurr::{cli, config};
use std::io;

use crate::structs::App;

/// Setup the application by processing the config and CLI data from the user
pub fn setup_app() -> Result<App, Error> {
    let writer = &mut io::stdout();
    let config = config::process_config_data(writer)?;
    let user_data = cli::process_cli_data(writer)?;
    let app = App {
        user_data,
        cache_file_path: config.cache_file_path,
        api_url: format!("https://v6.exchangerate-api.com/v6/{}", config.api_key),
    };

    Ok(app)
}
