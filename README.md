# Beer-Recipes
This was a school project that uses RecyclerView and Retrofit to show 10 beer recipes from [a given link](https://api.punkapi.com/v2/beers?page=1&per_page=10).

## Architecture

These are the files that the project contains, to show an overview of what was used.

![image](https://drive.google.com/uc?export=view&id=1bRPj7512faBWSxbPiM-jFn55K2fF60it)

## Application

The application has a RecyclerView on the main screen. This can be collapsed or expanded in order to show less/more details about the item.

![image](https://drive.google.com/uc?export=view&id=1eEkrN0qzCF3qMOWX-NFEusYZRM1C5mEe)

# Editing values

Pressing one of the items leads to an editing screen, where the values will be edited if they're correct, and if they're not, you can only continue editing or drop the changes made to that item.

![image](https://drive.google.com/uc?export=view&id=1oOBicqgO_W3m-R5DWPZSkeuZqiPTjOeL)

If the JSON from Ingredients is not correct, it won't be able to display the ingredients and so, you can't leave the editing screen with the new changes.

![image](https://drive.google.com/uc?export=view&id=12jOfhNFy_yrvAIs0LtRhxaOwOKUSxdrB)

