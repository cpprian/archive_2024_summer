/*
Copyright © 2022 Cyprian Szczepański <cpprian456@gmail.com>

*/
package cmd

import (
	"github.com/cpprian/flash-card-cli/card"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
)

// loadCmd represents the load command
var loadCmd = &cobra.Command{
	Use:   "load",
	Short: "Load all data from file.",
	Long: `Add all data from a file (default: ./data/question.csv
and save to ./data/data.csv`,
	Run: loadRun,
}

func loadRun(cmd *cobra.Command, args []string) {
	card.LoadData(viper.GetString("filename"))
}

func init() {
	rootCmd.AddCommand(loadCmd)
}
