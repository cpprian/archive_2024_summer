use clap::Parser;

/// Struct to hold the CLI arguments
#[derive(Clone, Debug, Parser)]
#[command(version, about, long_about = None)]
pub struct CliHandler {
    /// Base currency code
    #[clap(short, long, default_value = "USD")]
    pub base: String,

    /// Target currency code
    #[clap(short, long, default_value = "EUR")]
    pub target: String,

    /// Amount to convert
    #[clap(short, long, default_value = "10.0")]
    pub amount: f64,

    /// List all available currencies and their current exchange rates
    /// If this flag is set, only base argument is used
    #[clap(short, long, default_value = "false")]
    pub list: bool,

    /// Enable interactive mode for the CLI
    /// If this flag is set, the CLI will prompt the user for the base and target currencies and amount to convert
    /// If the list flag is set, this flag is ignored
    #[clap(short, long, default_value = "false")]
    pub interactive: bool,
}
