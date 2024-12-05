/*
Copyright © 2022 Cyprian Szczepański <cpprian456@gmail.com>

*/
package cmd

import (
	"encoding/csv"
	"log"
	"strings"
	"sync"

	"github.com/cpprian/flash-card-cli/card"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
)

// addCmd represents the add command
var addCmd = &cobra.Command{
	Use:   "add flashcard",
	Short: "Add question and answer separated by comma",
	Long: `Add flashcard separated by comma to data.csv.
You can also add more than one flashcard at the same time`,
	Run: addRun,
}

func addRun(cmd *cobra.Command, args []string) {
	card.NewCardContainer()
	
	var wg sync.WaitGroup
	for _, i := range args {
		wg.Add(1)
		go func(record string) {
			f, err := card.OpenFile(viper.GetString("datafile"))
			card.ErrOpenFile(f, err)
			defer f.Close()

			q := strings.Split(record, ",")
			csvWriter := csv.NewWriter(f)
			if err := csvWriter.Write(q); err != nil{
				log.Fatal()
			}
			csvWriter.Flush()
			wg.Done()
		}(i)
		wg.Wait()
	}
}

func init() {
	rootCmd.AddCommand(addCmd)
}
