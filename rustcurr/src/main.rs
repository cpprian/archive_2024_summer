use anyhow::{Context, Result};
use api::process;
use api::structs::ApiResponse;
use cache::save_cache;
use rustcurr::{
    api,
    converter::{self, structs::CurrencyConversion},
};
use std::io::Write;

mod cache;
mod setup;
mod structs;

fn main() -> Result<()> {
    run().context("Error running the app")?;
    Ok(())
}

fn run() -> Result<()> {
    let mut writer = &mut std::io::stdout().lock();
    // setup application
    let app = setup::setup_app().context("Error setting up the app")?;

    // load cache from a file
    let mut cache_item = cache::load_cache(app.cache_file_path.as_str(), &mut writer)?;
    if cache_item.base == app.user_data.base {
        writeln!(writer, "Using cache")?;
    } else {
        // process api response
        let response = process::process_api_response(app.api_url, &app.user_data)?;
        cache_item.base = app.user_data.base.clone();
        cache_item.api_response = response.clone();
    }

    // convert logic
    if app.user_data.list {
        print_list_of_currencies(cache_item.api_response.clone(), &mut writer)?;
    } else {
        let conversion = converter::process_conversion(
            cache_item.api_response.clone(),
            app.user_data.base.as_str(),
            app.user_data.target.as_str(),
            app.user_data.amount,
        )?;

        print_result_conversion(conversion, &mut writer)?;
    }

    // save cache to a file
    match save_cache(&cache_item, &app.cache_file_path) {
        Ok(_) => writeln!(writer, "Cache saved successfully")?,
        Err(e) => writeln!(writer, "Error saving cache: {}", e)?,
    }

    Ok(())
}

fn print_result_conversion(
    conversion: CurrencyConversion,
    writer: &mut impl std::io::Write,
) -> Result<()> {
    writeln!(writer, "\n----- Conversion Result -----")?;
    writeln!(writer, "{}\n", conversion)?;
    Ok(())
}

fn print_list_of_currencies(response: ApiResponse, writer: &mut impl std::io::Write) -> Result<()> {
    for (key, value) in response.conversion_rates.iter() {
        writeln!(writer, "{}: {}", key, value)?;
    }
    Ok(())
}
