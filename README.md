# Zadanie-Rekrutacyjne-Spring
Spring tasks

Relacja Report i User 

Opis relacji:
User (UserTable):

id: Klucz główny użytkownika.
login: Login użytkownika.
password: Hasło użytkownika.
role: Rola użytkownika.
Report:

id: Klucz główny raportu.
dataReport: Data raportu.
reportingPerson_id: Klucz obcy do użytkownika (User.id), określający osobę zgłaszającą raport.
reportUser: Użytkownik raportujący.
content: Treść raportu.
reportAddress: Adres raportu.
