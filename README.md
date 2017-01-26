# Mensa Plan Bielefeld University

This is an Alexa custom skill that finds meals offered at Bielefeld University's Mensa.

You can ask the following questions:

1. Your_Wakeup_Work, fragen mensa bielefeld {Day}
2. Your_Wakeup_Work, fragen mensa bielefeld {Day} {Menue}

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
* vital
* eintopf
* pasta
* salatbuffet
* beilagenbuffet
* aktions theke

## Special cases:

* "heute" (today) is automatically evaluated, e.g., heute --> montag (Monday)
* If no day is provided, "heute" is assumed.
* If no "Menue" is provided, "tagesmenue" (meal of the day) is assumed.
