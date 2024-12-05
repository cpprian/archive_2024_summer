use clap::Parser;

pub use crate::cli::structs::CliHandler;

use super::utils::prompt;

pub fn process_cli_data(writer: &mut impl std::io::Write) -> Result<CliHandler, anyhow::Error> {
    let cli = CliHandler::parse();
    // TODO: consider add this message as a log
    writeln!(writer, "CLI data loaded successfully")?;

    if cli.interactive {
        return process_interactive_mode(writer);
    }

    Ok(cli)
}

pub fn process_interactive_mode(
    writer: &mut impl std::io::Write,
) -> Result<CliHandler, anyhow::Error> {
    writeln!(writer, "\n---- Interactive mode ----")?;

    let base_currency = prompt(writer, "Enter the base currency (e.g., USD): ")?;
    let target_currency = prompt(writer, "Enter the target currency (e.g., PLN): ")?;
    let amount = prompt(writer, "Enter the amount to convert: ")?;

    let cli = CliHandler {
        base: base_currency,
        target: target_currency,
        amount: amount.parse()?,
        list: false,
        interactive: true,
    };
    Ok(cli)
}

// TODO: add tests
