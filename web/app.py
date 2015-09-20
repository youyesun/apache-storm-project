from flask import Flask, render_template, Response
import redis


app = Flask(__name__)
r = redis.StrictRedis(host='127.0.0.1', port=6379, db=0)


@app.route('/')
def show_homepage():
    return render_template("map.html")


if __name__ == '__main__':
    app.run(threaded=True,
    host='0.0.0.0'
)
