# MvpKit

A library that can help you reduce up to 80% of your business and presentation logic.

This library was created to reduce repeating and duplication of code that occurs in almost every project that uses MVP architecture where the developer has to write the stuff he's been writing for almost every project. Some developers (I can relate) copy and paste reusable code from different projects, but it's not as efficient as having all the code you need in a separate module that can be implemented with one line of code.

The library has well tested components, that can fit most real-life situations. The library is still under development and can cover more sides and fields by time. All pull requests and forks are welcomed.



# Architecture

Before going through library sections and components, let's have a look on it's architecture and the project structure it offers. The library uses a simple, clean and flexible MVP architecture that feels comfortable for most people.

Before we start, I must admit that I might have some gaps or wrong understanding of some concepts. Please if you notice anything wrong, start an issue, I will appreciate it!

**Basic MVP Explanation** :

![](https://miro.medium.com/max/2544/1*W6m93rWE1JVZafSy5U5wDQ.png)



- **VIEW** - This layer is everything that concerns the UI and what the user interacts with. The **View** acts like a slave to the **Presenter**, it does everything the **Presenter** says, and can't do anything without **Presenter's** permission (well maybe there are some cases where the **View** can do stuff without depending on the **Presenter** specially if the action concerns the UI only such as triggering an animation after button click or navigating back on back press).

   ![](https://drive.google.com/uc?export=download&id=1XfJxyIs-yTh8YI7u8UIxEv_oaQHQDswF)

  

  - The **View** mustn't contain any logic, what I mean by logic can refer to binary, or logical operations, mapping, parsing...

  - The **View** should receive and send only basic language data types such as `Strings` and `Integers`, it rarely requires to transfer an `Object`.

  - The **View** mustn't know anything about the **Model** layer.

    

- **PRESENTER** - This layer acts like a bridge between the **View** and the **Model** layers. It handles all the logic that concerns presentation such as binding. it usually handles simple binary and mathematical operations related to the **View** layer such as disallowing back presses in some conditions, form validations, mapping...

  

  ![](https://drive.google.com/uc?export=download&id=1mgnFoiI7LMPLawYI1fFoqolfrxZeX_U6)

  

  - The **Presenter** mustn't contain any business logic, what I mean by business can refer to data parsing, database queries, API calls...
  - The **Presenter** mustn't depend on any View related components, specially a Context. If your **Presenter** requires a `Context`, you're doing it wrong.

  > **Personal Opinion**: The less platform specific dependencies your Presenter has the cleaner it is.

  - The **Presenter** usually communicates with the **Model** layer through an **Interactor** or a **Repository**.
  - The **Presenter** must submit to the `Lifecycle` of it's slave **View**, means if your slave is dead, you must die too, or not? Well, in some cases, **Presenter** is kept alive to save instances if the **View** is expected to come back to life again like what happens during orientation changes. But the **Presenter** must still follow its **View's** lifecycle such as `onPause()` and `onDestroy()` in order to avoid `NullPointerException` while trying to communicate with the View after its death.



- **MODEL** - Some people might refer to the **Model** as `DataObjects` and `Entities`, while some others, including this library's concept, claim that the **Model** layer is everything that concerns business logic such as querying the database, making http calls, parsing, cashing... 

  Usually a **Repository** or/and an **Interactor** are implemented in order to handle this logic and deliver the result to the **Presenter**.

  

  ![](https://drive.google.com/uc?export=download&id=1xpRXsfI2VKukRuu_eJj1tPL9DcDCchS-)



**Extended MVP Explanation**:

![](https://mindorks.files.wordpress.com/2018/01/f220e-1g24y6clgqprbiwa-uodfew.png)



As you see in the picture above, there are two representations of each component for example **View** and **MvpView**, what are these? and what are they useful for?



- **CONTRACTS** -  Contracts are usually interfaces that require some components to implement some behaviours. In our scenario, contracts are used to provide more separation and flexibility. For example 

  we have a **ProfileScreen**. It has a **ProfilePresenter** that shouldn't communicate directly with **ProfileView** but should have some kind of contract between them. Let's create one and call it for example **ProfileContract**. It contains two contract interfaces, **View** and **Presenter**. Each component should implement its interface. These components must not be dependent on their concrete classes but on the implemented interfaces, so they will only expose what's declared in the contract.

  

  ![](https://drive.google.com/uc?export=download&id=1ZzF2OBI8WIivRLBdyJgPb1n54TYV1Mko)

  

  - Benefits of this approach is the ability to change concrete classes without breaking the code.

  > A noble Presenter once said: " Presenters should not care about who you are and where you come from, if you implement the contract, we're good to go!
  >
  >  ~ SettingsPresenter

  - Also, your code readability and flexibility increases. Your code will be clear and each component will exposes its contract's behaviour only, so if you want to add or remove something, you first do that in the contract and your IDE, (hopefully) will notify you of contract abusing in your code so you can take action faster and more effectively.
  - It also helps binding dependencies in an elegant way using Dependency Injection.
  - Contracts are not only implemented between the **View** and the **Presenter**, they can also be implemented by a **Repository**, an **Interactor**...

  

- **INTERACTOR** - Interactors are used usually when you have more than one data source, and your app consumes this data from different sources depending on some conditions. For example we have an application that supports cashing. The **Presenter** doesn't know and doesn't care where the data should come from, it tells the **Interactor** to deliver some data and waits for the response, the **Interactor** will first try to retrieve fresh data from the server, if something goes wrong, the **Interactor** will switch to the local data and will deliver what the **Presenter** asked for.



- **REPOSITORY** - Repositories are usually used for processing data, making database queries or executing http calls... It communicates directly with the data source, and acts like one when an **Interactor** is implemented. we have a Repository or more per data source. for example we have a **UserDatabase**  that is represented by a **UserDao**. The data exposed by this Dao is processed and controlled by a **UserRepository**.

  

- **DAO** - A Dao is an interface representation of your database, usually handled and created with the help of some ORMs. It exposes all the actions that can be done with the database and queries that can be applied.

  

- **CIENT** - A client is usually an interface representation of your server and REST API endpoints, it is responsible of creating server calls that will be executed, parsed and processed by a corresponding **Repository**.



- **PREFERENCES** - Preferences are key-value tables that are used to save states. For example saving a token after a successful login.



# Project Structure
soon
