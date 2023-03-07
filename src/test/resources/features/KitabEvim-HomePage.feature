Feature: KitabEvim-HomePage

  Scenario: Go to KitabEvim page
    * Go to "https://kitabevim.az/" address
    * 3 seconds wait
    * Check The Element "homePageContent"
    * Click to element "searchField"
    * Find element from csv file and write to element "searchField"
    * Press ENTER "searchField"
    * Hover and click the element "bookSelect"
    * Click to element "addToCartBtn"
    * 2 seconds wait
    * Wait on element "cartIconBtn"
    * Click to element "viewCartBtn"
    * Check if current URL contains the value "/cart"
    * Click to element "increased1Btn"
    * Click to element "refreshCartBtn"
    * Check The Element "pageMessagesField"
    * Check if element "pageMessagesField" contains text "Səbət yeniləndi."
    * Click to element "crossBtn"
    * Check The Element "emptyMessagesField"

  

    
