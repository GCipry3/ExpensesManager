from flask import Flask, render_template, request, redirect, url_for , flash
import requests
from requests.exceptions import JSONDecodeError

app = Flask(__name__)

app.secret_key = "secret"

base_url = "http://localhost:2030"

@app.route('/')
def index():
    try:
        members = requests.get(f"{base_url}/family").json()
        expenses = requests.get(f"{base_url}/family/expenses").json()
        budget = requests.get(f"{base_url}/family/budget").json()
    except Exception as e:
        return render_template('error.html', error_message=str(e))

    return render_template('index.html', members=members, expenses=expenses, budget=budget)


@app.route('/family/newMember', methods=['POST'])
def add_family_member():
    name = request.form['name']
    password = request.form['password']
    try:
        requests.post(f"{base_url}/family/newMember", data={'name': name, 'password': password})
    except Exception as e:
        flash(f"Error {e}", "danger")
        render_template('error.html', error_message=str(e))

    return redirect(url_for('index'))


@app.route('/family/addMoney', methods=['POST'])
def add_money():
    id = request.form['id']
    password = request.form['password']
    amount = request.form['amount']

    try:
        response = requests.post(f"{base_url}/family/login", data={'id': id, 'password': password}).json()
        if response['login'] == "true":
            requests.post(f"{base_url}/family/addMoney", data={'amount': amount})
            flash("Money added successfully.", "success")
        else:
            flash("Unsuccessful login. Please try again.", "danger")
    except Exception as e:
        flash(f"Error {e}", "danger")
        render_template('error.html', error_message=str(e))
    
    return redirect(url_for('index'))


@app.route('/family/spend', methods=['POST'])
def spend_money():
    id = request.form['id']
    password = request.form['password']
    expense_id = request.form['expense_id']

    try:
        response = requests.post(f"{base_url}/family/login", data={'id': id, 'password': password}).json()
        if response['login'] == "true":
            requests.post(f"{base_url}/family/spend", data={'id': expense_id})
            flash("Money spent successfully.", "success")
        else:
            flash("Unsuccessful login. Please try again.", "danger")
    except Exception as e:
        flash(f"Error {e}", "danger")
        render_template('error.html', error_message=str(e))

    return redirect(url_for('index'))


@app.route('/family/newExpense', methods=['POST'])
def add_expense():
    name = request.form['expense_name']
    price = request.form['expense_price']
    try:
        response = requests.post(f"{base_url}/family/expenses/newExpense", data={'name': name, 'price': price})
        if response.status_code == 200:
            flash("Expense added successfully.", "success")
        else:
            flash("Error adding expense. Please try again.", "danger")
    except Exception as e:
        flash(f"Error {e}", "danger")
        render_template('error.html', error_message=str(e))
    
    return redirect(url_for('index'))



if __name__ == '__main__':
    app.run(debug=True)
