# Contacts-Kotlin

The Contacts-Kotlin is a command-line application that allows you to manage your contacts. It provides functionality to add, view, search, and edit contacts. The application supports two types of contacts: Person Contact and Organization Contact.

## Features

- Add a new contact (Person Contact or Organization Contact) to the system.
- View all contacts in the system.
- Search for contacts by name, surname (for Person Contact), organization name (for Organization Contact), or phone number.
- Edit contact details such as name, surname, birth date (for Person Contact), address (for Organization Contact), and phone number.
- Display the creation and last edit timestamps for each contact.

## Technologies Used

- Kotlin: The application is written in Kotlin programming language.
- kotlinx.serialization: Used for serialization and deserialization of contact objects.
- Command-line Interface: The application is operated through the command line.

## Getting Started

To get started with the Contacts Management System, follow these steps:

1. Clone the repository: `git clone https://github.com/MYAUMUR/Contacts-Kotlin.git`
2. Navigate to the project directory: `cd Contacts-Kotlin`
3. (Optional) Compile the source code: `kotlinc src -include-runtime -d contacts.jar`
4. Run the application: `java -jar contacts.jar`

## Usage

Once the application is running, you can use the following commands:

- `add`: Add a new contact to the system.
- `list`: View all contacts in the system.
- `search`: Search for contacts by name, surname (for Person Contact), organization name (for Organization Contact), or phone number.
- `edit`: Edit contact details.
- `exit`: Exit the application.

Follow the on-screen prompts to input the required information for each command.

## Examples

1. Add a new Person Contact:

```
> add
Enter the name: John
Enter the surname: Doe
Enter the birth date: 1990-05-20
Enter the gender (M, F): M
Enter the number: 1234567890
Contact added successfully!
```

2. Search for contacts:

```
> search Doe
1. John Doe [no data]
```

3. Edit contact details:

```
> edit
Select a contact to edit (enter contact number): 1
Select a field (name, surname, birth, gender, number): name
Enter name: Jane
Contact updated successfully!
```

## Contributing

Contributions to the Contacts-Kotlin project are welcome. If you find any bugs or have suggestions for improvements, please open an issue or submit a pull request.

## License

The Contacts Management System is open-source software licensed under the [MIT license](LICENSE.md).
