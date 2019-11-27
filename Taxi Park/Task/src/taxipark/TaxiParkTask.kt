package taxipark

import java.lang.Math.floor
import kotlin.math.roundToInt

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filter { driver ->
            this.trips.none { trip -> trip.driver == driver }

        }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter { passenger ->
            this.trips.filter { trip ->
                trip.passengers.any { pass ->
                    pass == passenger
                }
            }.size >= minTrips
        }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter { passenger ->
            this.trips.filter { trip ->
                trip.driver == driver
                        && trip.passengers.any { p -> p == passenger }
            }.size > 1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter { passenger ->
            this.trips.filter { trip ->
                trip.passengers.any { p ->
                    p == passenger && (trip.discount ?: 0) != 0
                }
            }.size > this.trips.filter { tripb ->
                tripb.passengers.any { pp ->
                    pp == passenger && (tripb.discount ?: 0) == 0
                }
            }.size
        }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    if(this.trips.size == 0) return null
    val a: Map<Double, List<Trip>> = this.trips.groupBy { floor(it.duration.toDouble() / 10) * 10 }
    val (dur, trips) = a.maxBy { (_, trips)-> trips.size }!!

    return dur.toInt()..dur.toInt()+9
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {


    if (this.trips.isEmpty()) return false
    val sumAll: Double = this.trips.map{it.cost}.sum()
    println(this.trips.map{it.cost})
    println("the sum of all trip fare is $sumAll")
    val driverNum: Int = this.allDrivers.size;
    println("the number of driver is $driverNum")
    val twentyP: Int = floor(driverNum * 0.2).toInt()
    val a:Map<Driver, List<Trip>> = this.trips.groupBy { it.driver }
    println(a)
    val b = a.map { (driver, trips)->driver to (trips.map { it.cost }).sum() }
    println(b)
    val c = b.sortedBy { it.second}
    println(c)
    var sumTy: Double = 0.0
    println("number of derivers from 20% top is $twentyP")
    val num = c.size
    for( i in 1..twentyP){
        sumTy += c.get(num-i).second
    }
    println("the 20% top earns $sumTy")
    val aaaa = sumAll*0.8
    println("the 80% of all revenue is $aaaa")
    return  sumTy >= sumAll*0.8
}