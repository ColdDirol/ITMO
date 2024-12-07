package internal

enum class ActionTypes{
    REPLENISHMENT,
    DEBITING,
    TRANSFER_ON,    // transfer to user (plus)
    TRANSFER_OFF,   // transfet from user (minus)
}

enum class ExchangeRateStatus{
    ACTIVE,
    INACTIVE,
}