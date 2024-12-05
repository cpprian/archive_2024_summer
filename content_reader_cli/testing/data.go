package testing

import (
	con "github.com/cpprian/content_reader_cli/pkg/content_parser"
)

var (
	want_test_1 = con.BoxText{
		{
			Tag:  "h1",
			Text: "Hello world",
		},
		{
			Tag:  "p",
			Text: "a brand new text, to learn some new things",
		},
		{
			Tag:  "p",
			Text: "hello world new dir",
		},
		{
			Tag:  "p",
			Text: "Sick content",
		},
		{
			Tag:  "a",
			Text: "it should also appears",
		},
	}

	want_test_2 = con.BoxText{
		{
			Tag:  "h1",
			Text: "Hello",
		},
		{
			Tag: "b",
			Text: "world",
		},
		{
			Tag:  "p",
			Text: "a brand new text,",
		},
		{
			Tag:  "h6",
			Text: "to learn",
		},
		{
			Tag:  "p", // TODO: fix this
			Text: "some new things",
		},
		{
			Tag:  "div",
			Text: "some new nested content V",
		},
		{
			Tag:  "div",
			Text: "curiosity",
		},
		{
			Tag:  "p",
			Text: "hello world",
		},
		{
			Tag:  "i",
			Text: "new",
		},
		{
			Tag:  "strong",
			Text: "dir",
		},
		{
			Tag:  "i",
			Text: "the V",
		},
		{
			Tag: "p",
			Text: "content V",
		},
		{
			Tag:  "div",
			Text: "a new adventure V",
		},
		{
			Tag:  "p",
			Text: "Sick content",
		},
		{
			Tag:  "section",
			Text: "new content",
		},
		{
			Tag:  "a",
			Text: "it should also appears",
		},
		{
			Tag: "section",
			Text: "goodbye V",
		},
		{
			Tag:  "li",
			Text: "one",
		},
		{
			Tag:  "li",
			Text: "two",
		},
		{
			Tag:  "code",
			Text: "three",
		},
		{
			Tag:  "li",
			Text: "four",
		},
	}
)
