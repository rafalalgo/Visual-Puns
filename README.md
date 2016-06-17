** Projekt - Gra Kalambury. **

**Wykonawca:**
Rafał Byczek

**Krótka Instrukcja:**
Do poprawnego działania aplikacja potrzebuje lokalnie utworzonego użytkownika bazy danych postgres,którego to dane jak i dane samej bazy należy skonfigurować za pomocą pliku config.properties, w głównym katalogu aplikacji. Uruchamianie przebiega następująco, najpierw należy skompilować i uruchomić ServerApplication, które to pełni funkcję lokalnego serwera do gry, a następnie, można dodawać graczy do gry poprzez uruchamianie ClientAppliaction. 

**Opis:**
Z graczy, którzy grają w danym momencie jest wybierany ten, który rysował najdawniej z nich wszystkich. Na jego ekranie pojawia się płótno do rysowania i hasło, które ma narysować w czasie jednej minuty. Na ekranach innych graczy jest wyświetlany aktualnie rysowany obraz, a także wskazówki naprowadzające na hasło do zgadnięcia. Gracze mogą rozmawiać ze sobą za pomocą chatu, który zawiera aplikacja, a aktualny ranking jest na bieżąco aktualizowany. Punkty spadają liniowo od 100 do 0 przez okres minuty, a gracz który miał rysować w rundzie, w której hasło nie zostało zgadnięte dostaje minus 50 punktów kary.