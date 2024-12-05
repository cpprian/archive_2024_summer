/*
Copyright © 2022 Cyprian Szczepański <cpprian456@gmail.com>

*/
package card

import (
	"encoding/csv"
	"log"
	"os"
	"sync"

	"github.com/spf13/viper"
)

type Card struct {
	Question map[string]string
}

func NewCardContainer() *Card {
	return &Card{
		Question: map[string]string{},
	}
}

// ReadData reads from file all csv data and next saving to Card struct
func ReadData(filename string, c Card) {
	f, err := os.Open(filename)
	ErrOpenFile(f, err)
	defer f.Close()

	csvReader := csv.NewReader(f)
	records, err := csvReader.ReadAll()
	if err != nil {
		log.Fatal(err)
	}

	var wg sync.WaitGroup
	for _, i := range records {
		wg.Add(1)
		go func(i []string) {
			defer wg.Done()
			c.Question[i[0]] = i[1]
		}(i)
		wg.Wait()
	}
}

// SaveData saves to a file Card struct
func SaveData(c Card) {
	f, err := OpenFile(viper.GetString("datafile"))
	ErrOpenFile(f, err)
	defer f.Close()

	csvSaver := csv.NewWriter(f)
	defer csvSaver.Flush()
	var wg sync.WaitGroup
	for key, value := range c.Question {
		wg.Add(1)
		go func(key, value string) {
			defer wg.Done()
			r := make([]string, 0)
			r = append(r, key)
			r = append(r, value)
			err := csvSaver.Write(r)
			if err != nil {
				log.Fatal(err)
			}
		}(key, value)
		wg.Wait()
	}
}

// LoadData saves to data.csv all data from a file from the argument
func LoadData(filename string) {
	f, err := os.Open(filename)
	ErrOpenFile(f, err)
	defer f.Close()

	resultFile, err := OpenFile(viper.GetString("datafile"))
	ErrOpenFile(f, err)
	defer resultFile.Close()

	csvReader := csv.NewReader(f)
	records, err := csvReader.ReadAll()
	if err != nil {
		log.Fatal(err)
	}
	csvLoader := csv.NewWriter(resultFile)
	if err := csvLoader.WriteAll(records); err != nil {
		log.Fatal(err)
	}
}

func ErrOpenFile(f *os.File, err error) {
	if err != nil {
		log.Fatalf("Cannot open a file %v", f)
	}
}

func OpenFile(filename string) (*os.File, error) {
	return os.OpenFile(viper.GetString("datafile"), os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0644)
}

func CheckRecord(s string, c Card) bool {
	_, ok := c.Question[s]
	return ok
}