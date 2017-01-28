# Mensa Plan Bielefeld University

This is an Alexa custom skill that finds meals offered at Bielefeld University's Mensa.

You can ask the following questions:

* Alexa, fragen mensa bielefeld {Day}
* Alexa, fragen mensa bielefeld {Day} {Menue}
* Alexa, fragen  bielefeld was gibt es {Day}
* Alexa, fragen  bielefeld was gibt es {Day} {Menue}
* Alexa, fragen  bielefeld was gibt es {Day} als {Menue}
* Alexa, fragen  bielefeld was gibt es {Day} als {Menue} menue

## Where {Day} is a list:

* montag
* dienstag
* mittwoch
* donnerstag
* freitag
* samstag
* sonntag
* heute

## And where {Menue} is a list:

* tagesmenue
* vegetarisch
* vegetarisches
* vital
* vital menue
* eintopf
* pasta
* salatbuffet
* beilagenbuffet
* aktion

## Special cases:

* "heute" (today) is automatically evaluated, e.g., heute --> montag (Monday)
* If no day is provided, "heute" is assumed.
* If no "Menue" is provided, "tagesmenue" (meal of the day) is assumed.
