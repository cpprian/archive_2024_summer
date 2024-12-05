/*
Copyright © 2022 NAME HERE <EMAIL ADDRESS>

*/
package cmd

import (
	"encoding/json"
	"fmt"
	"github.com/cpprian/flash-card-cli/card"
	"github.com/spf13/cobra"
	"github.com/spf13/viper"
	"io"
	"log"
	"os"
	"time"
)

// startCmd represents the start command
var startCmd = &cobra.Command{
	Use:   "start",
	Short: "Program sends question and listens for user response",
	Long: `You can learn from from flash cards by using this command.

Available options:
	time - set time for your session
	num - set how many question you would like to answer
	all - program serves all questions`,
	Run: startRun,
}

var (
	amountOfTime     int
	howManyQuestions int
	all              bool
)

func startRun(cmd *cobra.Command, args []string) {
	c := card.NewCardContainer()
	card.ReadData(viper.GetString("datafile"), *c)
	score := card.NewScoreContainer(c)

	timer := time.NewTimer(time.Duration(amountOfTime) * time.Second)
	goodAnswer := 0
	counter := 0
test:
	for k, v := range c.Question {
		if counter == howManyQuestions && !all {
			break
		}
		ch := make(chan string)
		go func(question string) {
			var answer string
			fmt.Println(k)
			t0 := time.Now()
			fmt.Scanf("%s\n", &answer)
			t1 := time.Now()

			sd := card.NewScoreDetail()
			sd.Card.Question[k] = v
			sd.TimeForQuestion = t1.Sub(t0)
			sd.ClientAnswer = answer
			score.Info = append(score.Info, *sd)
			ch <- answer
		}(k)

		select {
		case answer := <-ch:
			if answer == v {
				goodAnswer++
			}
		case <-timer.C:
			fmt.Println("time is up")
			close(ch)
			break test
		}

		counter++
	}

	score.GoodQuestions = goodAnswer
	fmt.Println("the end of the flash card session")
	a, err := json.Marshal(score)
	if err != nil {
		log.Fatalf("Unable to create score %v\n", err)
	}

	f, err := os.OpenFile(viper.GetString("scorefile"), os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0644)
	if err != nil {
		log.Fatalf("Unable to open file %v", err)
	}

	_, err = io.WriteString(f, string(a))
	if err != nil {
		log.Fatalf("Unable to save data %v", err)
	}
}

func init() {
	rootCmd.AddCommand(startCmd)

	startCmd.Flags().IntVar(&amountOfTime, "time", 100, "Amount of time for all questions")
	startCmd.Flags().IntVar(&howManyQuestions, "num", 10, "How many questions user would like to answer")
	startCmd.Flags().BoolVar(&all, "all", false, "User would like to display all questions")
}
