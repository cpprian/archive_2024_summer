import requests, random, logging as log
import random, json, time

# TODO: speed up func
def random_data(size):
    i = 100
    if size < 100: # api can handle max 100 records
        i = size
    
    m = 0
    x = 0
    s = size/2
    amount_data = i
    response_name = list()
    response_address = list()
    data = list()
    while i <= size:
        log.warning(f"Generating data: {int((i/size) * 100)}%")
        url_name = f"https://random-data-api.com/api/name/random_name?size={amount_data}"
        url_address = f"https://random-data-api.com/api/address/random_address?size={amount_data}"
        response_name.append(requests.get(url_name))
        response_address.append(requests.get(url_address))

        if response_name[m].status_code != 200 and response_address[m].status_code != 200:
            log.error("Error: API request failed")
            return None

        k = 0
        while x < i:
            data.append(dict())
            data[x]['Firstname'] = response_name[m].json()[k]['first_name']
            data[x]['Lastname'] = response_name[m].json()[k]['last_name']
            data[x]['Age'] = random.randint(18, 100)
            data[x]['City'] = response_address[m].json()[k]['city']
            data[x]['Street'] = response_address[m].json()[k]['street_name']
            data[x]['Zipcode'] = response_address[m].json()[k]['zip']
            data[x]['Country'] = response_address[m].json()[k]['country']
            x += 1
            k += 1

        m += 1
        size -= 100
        if size <= 0:
            break
        elif size < 100:
            i = size
            amount_data = size
        else:
            i += 100

    # return json array
    # Firstname, Lastname, Age, City, Street, Zipcode, Country

    return json.dumps(data)