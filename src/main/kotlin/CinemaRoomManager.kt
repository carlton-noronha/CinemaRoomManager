package cinema

fun main() {

    // Parameters for Cinema
    println("Enter the number of rows:")
    val rows: Int = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow: Int = readLine()!!.toInt()

    val cinemaSeating = createCinemaLayout(rows, seatsInRow)

    do {
        println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
        val option: Int = readLine()!!.toInt()

        when (option) {
            1 -> showTheSeats(cinemaSeating, rows, seatsInRow)
            2 -> getSeatDetails(cinemaSeating, rows, seatsInRow)
            3 -> obtainStatistics(cinemaSeating, rows, seatsInRow)
        }

    } while (option != 0)

}

// Get row and seat
fun getSeatDetails(cinemaSeating: Array<CharArray>, rows: Int, seatsInRow: Int) {
    println()
    println("Enter a row number:")
    val row: Int = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val seat: Int = readLine()!!.toInt()
    bookSeat(cinemaSeating, row, seat, rows, seatsInRow)
}

// Book seat
fun bookSeat(cinemaSeating: Array<CharArray>, row: Int, seat: Int, rows: Int, seatsInRow: Int) {
    if (row <= rows && seat <= seatsInRow) {
        if (cinemaSeating[row][seat] == 'S') {
            cinemaSeating[row][seat] = 'B'
            printTicketPrice(rows, seatsInRow, row)
            println()

        } else {
            println()
            println("That ticket has already been purchased!")
            getSeatDetails(cinemaSeating, rows, seatsInRow)
        }
    } else {
        println()
        println("Wrong input!")
        getSeatDetails(cinemaSeating, rows, seatsInRow)
    }
}

// Layout of Cinema
fun createCinemaLayout(rows: Int, seatsInRow: Int): Array<CharArray> {
    var cinemaSeating = emptyArray<CharArray>()
    for (i in 0..rows) {
        val seats = CharArray(seatsInRow + 1)
        for (j in 0..seatsInRow) {
            if (i == 0) {
                if (j == 0) {
                    seats[j] = ' '
                } else {
                    seats[j] = (48 + j).toChar()
                }
            } else {
                if (j == 0) {
                    seats[j] = (48 + i).toChar()
                } else {
                    seats[j] = 'S'
                }
            }
        }
        cinemaSeating += seats
    }
    println()
    return cinemaSeating
}

// Displaying the Cinema layout
fun showTheSeats(cinemaSeating: Array<CharArray>, rows: Int, seatsInRow: Int) {
    println()
    println("Cinema:")
    for (i in 0..rows) {
        for (j in 0..seatsInRow) {
            print("${cinemaSeating[i][j]} ")
        }
        println()
    }
    println()
}

// Print ticket price
fun printTicketPrice(rows: Int, seatsInRow: Int, row: Int) {
    println()
    print("Ticket Price:")
    println(
        if (isSeatsLessThanEqual60(rows, seatsInRow)) {
            "$10"
        } else {
            val half: Int = rows / 2
            if (row <= half) {
                "$10"
            } else {
                "$8"
            }
        }
    )
}

// Obtain Statistics on sold tickets
fun obtainStatistics(cinemaSeating: Array<CharArray>, rows: Int, seatsInRow: Int) {
    println()
    val soldTicketCount = countPurchasedTickets(cinemaSeating, rows, seatsInRow, 1)
    println("Number of purchased tickets: $soldTicketCount")
    println("Percentage: %.2f".format(soldTicketCount * 100f / (rows * seatsInRow)) + "%")
    println("Current income: $" + currentIncome(cinemaSeating, rows, seatsInRow))
    println("Total income: $" + totalIncome(rows, seatsInRow))
    println()
}

// Count number of purchased tickets
fun countPurchasedTickets(cinemaSeating: Array<CharArray>, rows: Int, seatsInRow: Int, start: Int): Int {
    var count = 0
    for (i in start..rows) {
        for (j in 1..seatsInRow) {
            if (cinemaSeating[i][j] == 'B') {
                ++count
            }
        }
    }
    return count
}

// Calculate current income
fun currentIncome(cinemaSeating: Array<CharArray>, rows: Int, seatsInRow: Int): Int {
    return if (isSeatsLessThanEqual60(rows, seatsInRow)) {
        countPurchasedTickets(cinemaSeating, rows, seatsInRow, 1) * 10
    } else {
        val half: Int = rows / 2
        countPurchasedTickets(cinemaSeating, half, seatsInRow, 1) * 10 +
                countPurchasedTickets(
                    cinemaSeating, rows, seatsInRow,
                    if (rows % 2 == 0) rows - half + 1 else rows - half
                ) * 8
    }
}

// Calculate max possible income
fun totalIncome(rows: Int, seatsInRow: Int): Int {
    return if (isSeatsLessThanEqual60(rows, seatsInRow)) {
        rows * seatsInRow * 10
    } else {
        val half: Int = rows / 2
        half * seatsInRow * 10 + seatsInRow * (rows - half) * 8
    }
}

// Check if cinema has less than or equal to 60 seats
fun isSeatsLessThanEqual60(rows: Int, seatsInRow: Int): Boolean {
    return rows * seatsInRow <= 60
}