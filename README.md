![Open Source Love](https://img.shields.io/badge/Open%20Source-%E2%9D%A4-red.svg)
![GitHub](https://img.shields.io/github/license/isfaaghyth/rx-priority-scheduler.svg)
![GitHub forks](https://img.shields.io/github/forks/isfaaghyth/rx-priority-scheduler.svg)
![GitHub issues](https://img.shields.io/github/issues/isfaaghyth/rx-priority-scheduler.svg)
![GitHub pull requests](https://img.shields.io/github/issues-pr/isfaaghyth/rx-priority-scheduler.svg)
![GitHub contributors](https://img.shields.io/github/contributors/isfaaghyth/rx-priority-scheduler.svg)
![GitHub top language](https://img.shields.io/github/languages/top/isfaaghyth/rx-priority-scheduler.svg)

# Material Menu
Menus display a list of choices on temporary surfaces.



## Sample Usage

Step 1. Define a spinner view in your layout.
```xml
<isfaaghyth.app.spinner.SpinnerMenu
        android:id="@+id/spinner_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

Step 2. Create a model and implement `ItemContent`.
```kotlin
data class Person(val name: String, val age: Int): ItemContent {
    override fun menuItem(): String = name
    override fun menuSubItem(): String = '$age' //optional, you can replace with empty like ''.
}
```

Step 3. Show it!
```kotlin
//create a personList
val personList = arrayListOf<Person>()
personList.add("Isfha", 21)
personList.add("Ghiath", 20)

//define in your activity
spinner_menu.setItems(personList())
spinner_menu.get(object: MenuItemListener<Person>() { //handle on click.
    override public fun onClick(Person item) {
         Log.d("TAG", item.name);
    }
})
```

### Custom Attributes

| Type |         Name        |
|:---------:|:---------------------:|
|     boolean     | app:searchable                   |
|     boolean     | app:sub_item_visible                   |

### Snapshot

![Default](https://raw.githubusercontent.com/isfaaghyth/material-menu/master/1.jpg)

with searchable:

![Default](https://raw.githubusercontent.com/isfaaghyth/material-menu/master/2.jpg)

### Let's go.

Step 1. Add the JitPack repository to your build file

```javascript
allprojects {
      repositories {
         ...
         maven { url 'https://jitpack.io' }
      }
}
```

Step 2. Add the dependency

```javascript
dependencies {
      implementation 'com.github.isfaaghyth:material-menu:1.0'
}
```


Apache License 2.0 🤘
