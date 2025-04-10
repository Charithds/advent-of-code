package rbtree

type Int int

func (x Int) Less(than Item) bool {
	return x <= than.(Int)
}

type Uint32 uint32

func (x Uint32) Less(than Item) bool {
	return x <= than.(Uint32)
}

type String string

func (x String) Less(than Item) bool {
	return x <= than.(String)
}
