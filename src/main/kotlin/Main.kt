fun main(args: Array<String>) {

    val path = System.getProperty("user.home")

    val fileObserver = FileObserver(mutableSetOf())
    val fileSubscriber = FileSubscriber()
    fileObserver.registerSubscriber(fileSubscriber)
    fileObserver.startObservation(path)

    //Observer Pattern

    //FileObserver observers file for changes

    //FileSensorSubscriber register at FileSensor



}