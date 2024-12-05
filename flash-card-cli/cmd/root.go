/*
Copyright © 2022 Cyprian Szczepanski <cpprian456@gmail.com>

*/
package cmd

import (
	"log"
	"os"

	"github.com/spf13/cobra"
	"github.com/spf13/viper"
)

// rootCmd represents the base command when called without any subcommands
var rootCmd = &cobra.Command{
	Use:   "flash-card-cli",
	Short: "Learn from cli app flashcards.",
	Long: `The App allows you to save all your questions and answers
and learn from them by simply sending questions and writing an answer.
When you end you can see your score.`,
}

// Execute adds all child commands to the root command and sets flags appropriately.
// This is called by main.main(). It only needs to happen once to the rootCmd.
func Execute() {
	err := rootCmd.Execute()
	if err != nil {
		os.Exit(1)
	}
}

var (
	configName = "./conf/config.yaml"
)

func initConfig() {
	viper.SetConfigFile(configName)

	if err := viper.ReadInConfig(); err != nil {
		log.Fatal(err)
	}
}

func init() {
	cobra.OnInitialize(initConfig)
}
