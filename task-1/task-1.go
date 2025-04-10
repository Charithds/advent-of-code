package task1

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"

	"example.com/advent-of-code/rbtree"
)

// read the file
// append the values to two arrays
// sort while appending itself
// take the difference

func readDataIntoArrays(scanner *bufio.Scanner) (*rbtree.Rbtree, *rbtree.Rbtree) {
	tree1 := rbtree.New()
	tree2 := rbtree.New()

	for scanner.Scan() {
		words := strings.Fields(scanner.Text())
		// fmt.Printf("line: %s\n", scanner.Text())
		
		value1, _ := strconv.Atoi(words[0])
		value2, _ := strconv.Atoi(words[1])
		tree1.Insert(rbtree.Int(value1))
		tree2.Insert(rbtree.Int(value2))
	}

	return tree1, tree2
}

func calculateDiff(tree1 *rbtree.Rbtree, tree2 *rbtree.Rbtree) int {
	var it1 *rbtree.Node = tree1.First()
	var it2 *rbtree.Node = tree2.First()
	// it2 := tree2.Min()

	distance := 0
	c := 0
	for it1.Item != nil && it2.Item != nil {
		intValue1 := it1.Item.(rbtree.Int) 
		intValue2 := it2.Item.(rbtree.Int) 
	
		value := int(intValue1) - int(intValue2)
		if value < 0 { value *= -1 }
		distance += value
		// fmt.Printf("values %d %d\n", int(intValue1), int(intValue2))

		it1 = tree1.Next(it1)
		it2 = tree2.Next(it2)
		c++
	}
	fmt.Println("count count " + strconv.Itoa(c))
	return distance
}

func Task1() {
	dataPath := "task-1/task-1.dat"
	f, err := os.Open(dataPath)
	if err != nil {
		log.Fatal(err)
	}

	defer f.Close()

	scanner := bufio.NewScanner(f)
	
	tree1, tree2 := readDataIntoArrays(scanner)
	fmt.Printf("Count in tree1 is %d\n", tree1.Len())
	fmt.Printf("Min in tree1 is %d\n", tree1.Min())
	fmt.Printf("Max in tree1 is %d\n", tree1.Max())

	fmt.Printf("Count in tree2 is %d\n", tree2.Len())
	fmt.Printf("Min in tree2 is %d\n", tree2.Min())
	fmt.Printf("Max in tree2 is %d\n", tree2.Max())

	distance :=	calculateDiff(tree1, tree2)
	fmt.Println("Distance is " + strconv.Itoa(distance))
}


