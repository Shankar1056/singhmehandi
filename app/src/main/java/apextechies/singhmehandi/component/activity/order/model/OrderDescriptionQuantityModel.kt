package apextechies.singhmehandi.component.activity.order.model

class OrderDescriptionQuantityModel {
    var descriptionName: String? = null
    var Quantity: String? = null
    var desc_id: String? = null

    constructor(descriptionName: String, desc_id: String) {
        this.descriptionName = descriptionName
        this.desc_id = desc_id
    }

    constructor(Quantity: String) {
        this.Quantity = Quantity
    }

}