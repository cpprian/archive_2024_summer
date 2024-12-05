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
	"log"
	"net/http"

	"github.com/cpprian/content_reader_cli/pkg/content_parser"
	pdf "github.com/cpprian/content_reader_cli/pkg/pdf_maker"
	"github.com/spf13/cobra"
)

// webCmd represents the web command
var webCmd = &cobra.Command{
	Use:   "web",
	Short: "Create a pdf file from your web pages.",
	Long: `This command will create a pdf file with all links that you provide.
	
Remeber that you can change configuration for the pdf maker if you want
another font style or name of the file etc.`,
	Run: func(cmd *cobra.Command, args []string) {
		hrefParser := content_parser.NewParser()
		hrefArray := &[]content_parser.BoxText{}

		for _, arg := range args {
			hrefData, err := http.Get(arg)
			if err != nil {
				log.Printf("Cannot open %v, error GET: %v\n", arg, hrefData)
				continue
			}
			if err := hrefParser.CreateBoxText(hrefData.Body); err != nil {
				log.Fatalln(err)
				continue
			}
			*hrefArray = append(*hrefArray, *hrefParser)
		}

		for _, href := range *hrefArray {
			href.Print()
		}
		log.Println("OK")
		if err := pdf.CreatePdf(hrefParser); err != nil {
			log.Println(err)
		}
	},
}

func init() {
	rootCmd.AddCommand(webCmd)
}
