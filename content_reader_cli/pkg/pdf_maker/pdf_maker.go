package pdf_maker

import (
	"math"
	"strings"

	"github.com/cpprian/content_reader_cli/pkg/content_parser"
	_ "github.com/johnfercher/maroto/pkg/color"
	"github.com/johnfercher/maroto/pkg/consts"
	"github.com/johnfercher/maroto/pkg/pdf"
	"github.com/johnfercher/maroto/pkg/props"
	"github.com/spf13/viper"
)

func CreatePdf(hrefParser *content_parser.BoxText) error {
	m := pdf.NewMaroto(consts.Portrait, consts.A4)
	m.SetPageMargins(20, 10, 20)
	m.AddUTF8Font(viper.GetString("font.style"), consts.Normal, "./config/font/"+viper.GetString("font.style")+".ttf")
	m.SetDefaultFontFamily(viper.GetString("font.style"))

	fillDataIntoPdf(m, wrapText(hrefParser))

	return m.OutputFileAndClose("./store/" + viper.GetString("name") + ".pdf")
}

func fillDataIntoPdf(m pdf.Maroto, data string) {
	size := calculateRowSize(data)

	m.Row(size, func() {
		m.Col(12, func() {
			m.Text(data, props.Text{
				Size: float64(viper.GetInt("font.size")),
			})
		})
	})
}

func wrapText(text *content_parser.BoxText) string {
	var result strings.Builder
	for _, t := range *text {
		result.WriteString(t.Text + " ")
	}
	return result.String()
}

func calculateRowSize(text string) float64 {
	return math.Ceil(float64(len(text)) * float64(viper.GetInt("font.size")) / 150)
}
