package card

import "time"

type ScoreDetail struct {
	Card            Card
	ClientAnswer    string
	TimeForQuestion time.Duration
}

type Score struct {
	Info           []ScoreDetail
	Date           time.Time `json:"time"`
	GoodQuestions  int       `json:"score"`
	TotalQuestions int       `json:"total"`
}

func NewScoreDetail() *ScoreDetail {
	return &ScoreDetail{
		Card:            *NewCardContainer(),
		ClientAnswer:    "",
		TimeForQuestion: 0,
	}
}

func NewScoreContainer(c *Card) *Score {
	return &Score{
		Info:           []ScoreDetail{},
		Date:           time.Now(),
		GoodQuestions:  0,
		TotalQuestions: len(c.Question),
	}
}
