package testing

import (
	"testing"

	"github.com/cpprian/flash-card-cli/card"
)

func BenchmarkReadData(b *testing.B) {
	c := card.NewCardContainer()
	for n := 0; n < b.N; n++ {
		card.ReadData("test.csv", *c)
	}
}

func BenchmarkSaveData(b *testing.B) {
	c := card.NewCardContainer()
	card.ReadData("test.csv", *c)
	for n := 0; n < b.N; n++ {
		card.SaveData(*c)
	}
}
