# AutofillDemo App
 An autofill utility can help users to minimize time wasting on writing the same data for rendering over and over again. 
Android O brings autofill functionality to Android apps. As a developer, you can use the new Autofill Framework to create your own custom autofill service, tailored to your own needs. 
## Prerequisites
Android Studio 2.4 Preview 7 or higher <br/>
An emulator or device running `Android Oreo or higher`<br/>
* Note: This sample service stores all `autofill` data in SharedPreferences (Local storage). Be careful about what you store when experimenting with the sample because anyone with root access to your device will be able to view your autofill data.<br/>

![autofill](https://user-images.githubusercontent.com/29621738/57722403-fbeef880-76a3-11e9-9fa0-e5258cc8e7ab.gif)

<br/>

The sample's Autofill service is implemented to parse the client's view hierarchy in search of `autofillable` fields that it has data for. If such fields exist in the hierarchy, the service sends data suggestions to the client to autofill those fields. The client uses the following attributes to specify autofill properties: `importantForAutofill`, `autofillHints`, and `autofillType`. importantForAutofill specifies whether the view is autofillable. autofillHints is a list of strings that hint to the service what data to fill the view with. This sample service only supports the hints listed here with the prefix AUTOFILL_HINT_*. autofillType tells the service the type of data it expects to receive (i.e. a list index, a date, or a string). Specifying autofillType is only necessary when implementing a custom view since all of the provided widgets in the UI toolkit do this for you.<br/>
To set the device's default Autofill service to the one in the sample, edit _Settings_ > _System_ > *Languages & Input* > *Advanced* > *Auto-fill service* and select the sample app.<br/>



