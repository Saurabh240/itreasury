import subprocess
from bs4 import BeautifulSoup
import json
import time

# Base URL of the website
BASE_URL = "https://bank.codes/swift-code/india/page"

# Function to fetch data from a single page using curl
def fetch_page_data():
    
    # Parse the HTML file
    with open("output.html", "r", encoding="utf-8") as file:
        soup = BeautifulSoup(file, "html.parser")
    
    # Find the table containing the data
    table = soup.find("table", {"class": "swift-country"})
    if not table:
        return []

    rows = table.find_all("tr")[1:]  # Skip the header row
    data = []
    for row in rows:
        columns = row.find_all("td")
        if len(columns) >= 5:
            id = columns[0].text.strip()
            bank_name = columns[1].text.strip()
            city = columns[2].text.strip()
            branch = columns[3].text.strip()
            swift_code = columns[4].text.strip()
            data.append({
                "id": id,
                "bank_name": bank_name,
                "city": city,
                "branch": branch,
                "swift_code": swift_code
            })
    return data

# Function to fetch data from all pages
def fetch_all_data():
    all_data = []
    page_number = 2

    while True:
        print(f"Fetching data from page {page_number}...")
        page_url = f"{BASE_URL}/{page_number}/"
        try:
            # Run the curl command to fetch the HTML content
            curl_command = [
                "curl",
                "-s",  # Silent mode
                "-o", "output.html",  # Save output to a file
                "-H", "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36",
                page_url
            ]
            # subprocess.run(curl_command, check=True)

            page_data = fetch_page_data()
            if not page_data:  # Stop if no data is found on the page
                break
            all_data.extend(page_data)
            page_number += 1
            time.sleep(2)  # Add delay to avoid being blocked
            break
        except subprocess.CalledProcessError as e:
            print(f"Error fetching page {page_number}: {e}")
            break

    return all_data

# Main script
if __name__ == "__main__":
    print("Starting data fetch...")
    data = fetch_all_data()
    print(f"Fetched {len(data)} records.")

    # Save data to a JSON file
    output_file = "swift_codes.json"
    with open(output_file, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=4, ensure_ascii=False)
    
    print(f"Data saved to {output_file}")