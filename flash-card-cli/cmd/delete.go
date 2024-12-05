/*
Copyright © 2022 Cyprian Szczepański <cpprian456@gmail.com>

*/
package cmd

import (
	"fmt"
	"log"
	"os"
	"sync"

	"github.com/cpprian/flash-card-cli/card"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
)

// deleteCmd represents the delete command
var deleteCmd = &cobra.Command{
	Use:   "delete",
	Short: "Delete question with answer from data.csv",
	Long: `You can delete more than one questions.
Write only questions separeted by space to delete data from a file.`,
	Run: deleteRun,
}

func deleteRun(cmd *cobra.Command, args []string) {
	c := card.NewCardContainer()
	card.ReadData(viper.GetString("datafile"), *c)

	var wg sync.WaitGroup
	for _, i := range args {
		wg.Add(1)
		go func(record string) {
			defer wg.Done()
			if ok := card.CheckRecord(record, *c); !ok {
				fmt.Printf("Question: %s doesn't exist\n", record)
				return
			}
			delete(c.Question, record)
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
	rootCmd.AddCommand(deleteCmd)
}
