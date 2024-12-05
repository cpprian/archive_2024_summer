package content_parser

import (
	"fmt"
	"io"
	"strings"

	"golang.org/x/net/html"
)

type TextStruct struct {
	Tag, Text string
}

type BoxText []TextStruct

func NewParser() *BoxText {
	return &BoxText{}
}

func tagChecker(tag string) bool {
	switch tag {
	case "div", "p", "h1", "h2", "h3", "h4", "h5", "h6", "a", "code",
		"pre", "big", "i", "strong", "b", "section", "header",
		"article", "ul", "li", "ol":
		return true
	}
	return false
}

func (b *BoxText) CreateBoxText(r io.Reader) error {
	doc, err := html.Parse(r)
	if err != nil {
		return err
	}

	b.Parse(doc)

	return nil
}

func (b *BoxText) Parse(n *html.Node) {
	node, err := getBody(n)
	if err != nil {
		return
	}

	var saveContent func(n *html.Node)
	saveContent = func(n *html.Node) {
		if n.Type == html.ElementNode && tagChecker(n.Data) {
			newNode := ParseNode(n, n.Data)
			if newNode != nil {
				*b = append(*b, *newNode)
			}
		}

		// TODO: find a better way to parse nested tags
		if n.Type == html.TextNode {
			text := strings.TrimSpace(n.Data)
			if len(text) > 1 && b.SearchForOccurence(text) {
				if tagChecker(n.Parent.Data) {
					*b = append(*b, TextStruct{
						// TODO: better way to get tag name
						Tag:  n.Parent.Data,
						Text: text,
					})
				}
			}
		}

		for c := n.FirstChild; c != nil; c = c.NextSibling {
			saveContent(c)
		}
	}
	saveContent(node)
}

func getBody(n *html.Node) (*html.Node, error) {
	var body *html.Node

	var searchForBody func(n *html.Node)
	searchForBody = func(n *html.Node) {
		if n.Type == html.ElementNode && n.Data == "body" {
			body = n
			return
		}

		for c := n.FirstChild; c != nil; c = c.NextSibling {
			searchForBody(c)
		}
	}
	searchForBody(n)

	if body != nil {
		return body, nil
	}
	return nil, fmt.Errorf("cannot find body")
}

func ParseNode(n *html.Node, tag string) *TextStruct {
	if c := n.FirstChild; c != nil {
		if c.Type == html.TextNode {
			text := strings.TrimSpace(c.Data)
			
			if len(text) > 1 {
				return &TextStruct{
					Tag:  tag,
					Text: text,
				}
			}
		}
	}
	
	return nil
}

func (b *BoxText) SearchForOccurence(text string) bool {
	for _, v := range *b {
		if v.Text == text {
			return false
		}
	}
	return true
}

func (b *BoxText) Print() {
	for i, v := range *b {
		fmt.Printf("#%d %s: %s\n", i, v.Tag, v.Text)
	}
}