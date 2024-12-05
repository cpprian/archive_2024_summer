/*
Copyright © 2022 Cyprian Szczepański <cpprian456@gmail.com>

*/
package cmd

import (
	"fmt"
	"log"
	"os"
	"strings"
	"sync"

	"github.com/cpprian/flash-card-cli/card"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
)

// updateCmd represents the update command
var updateCmd = &cobra.Command{
	Use:   "update",
	Short: "Update question with new answer",
	Long: `You can update more than one questions.
Write question with answer separeted by comma to delete change data from a file.`,
	Run: updateRun,
}

func updateRun(cmd *cobra.Command, args []string) {
	c := card.NewCardContainer()
	card.ReadData(viper.GetString("datafile"), *c)

	var wg sync.WaitGroup
	for _, i := range args {
		wg.Add(1)
		go func(record string) {
			defer wg.Done()
			d := strings.Split(record, ",")
			if ok := card.CheckRecord(d[0], *c); !ok {
				fmt.Printf("Question: %s doesn't exist! Use command \n\t ./myapp add \n", record)
				return
			}
			c.Question[d[0]] = d[1]
		}(i)
		wg.Wait()
	}
	
	f, err := os.Create(viper.GetString("datafile"))
	if err != nil {
		log.Fatalf("Cannot create a file: %v", f)
	}
	card.SaveData(*c)
}

func init() {
	rootCmd.AddCommand(updateCmd)

	// Here you will define your flags and configuration settings.

	// Cobra supports Persistent Flags which will work for this command
	// and all subcommands, e.g.:
	// updateCmd.PersistentFlags().String("foo", "", "A help for foo")

	// Cobra supports local flags which will only run when this command
	// is called directly, e.g.:
	// updateCmd.Flags().BoolP("toggle", "t", false, "Help message for toggle")
}
