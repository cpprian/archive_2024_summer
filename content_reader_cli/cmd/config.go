/*
Copyright © 2022 cpprian <cpprian456@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package cmd

import (
	"fmt"
	"log"

	"github.com/spf13/cobra"
	"github.com/spf13/viper"
)

type ConfigStruct struct {
	Name   string
	Open   bool
	Pretty bool

	Font struct {
		Size  int
		Style string
	}
}

var (
	userConfig *ConfigStruct

	configCmd = &cobra.Command{
		Use:   "config",
		Short: "Settings for the pdf file maker.",
		Long: `You can configure a basic design of your pdf files.
		
Once you have changed configuration,
the next usage of application will be with the same settings until you change again`,
		Run: func(cmd *cobra.Command, args []string) {
			// TODO: inform user that process ends with positive or negative result
			fmt.Println("config called")
		},
	}
)

var (
	configFileName = "content_reader"
	configPath     = "./config"
)

// initConfig reads in config file and ENV variables if set.
func initConfig() {
	viper.AddConfigPath(configPath)
	viper.SetConfigType("yaml")
	viper.SetConfigName(configFileName)

	viper.SetDefault("name", userConfig.Name)
	viper.SetDefault("font.size", userConfig.Font.Size)
	viper.SetDefault("font.style", userConfig.Font.Style)
	viper.SetDefault("pretty", userConfig.Pretty)
	viper.WriteConfig()
	viper.AutomaticEnv()

	// If a config file is found, read it in.
	if err := viper.ReadInConfig(); err != nil {
		log.Println(err)
	}
}

func init() {
	rootCmd.AddCommand(configCmd)

	userConfig = &ConfigStruct{}
	configCmd.Flags().StringVarP(&userConfig.Name, "name", "n", "document", "A name of your new pdf file (by default 'document_{number_of_document_files + 1}')")
	configCmd.Flags().StringVarP(&userConfig.Font.Style, "style", "t", "sans_serif", "A font style, that your pdf will be writing (by default Sans serif)")
	configCmd.Flags().IntVarP(&userConfig.Font.Size, "size", "z", 14, "A font size, that your pdf will be writing (by default 14)")
	configCmd.Flags().BoolVarP(&userConfig.Pretty, "pretty", "r", false, "Is your pdf file should embelished html tags and code formats (by default false)")
	
	cobra.OnInitialize(initConfig)
}
