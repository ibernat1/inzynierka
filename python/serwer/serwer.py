# -*- coding: utf-8 -*-
from flask import Flask, send_file, request, jsonify
import os

app = Flask(__name__)

# Ścieżka do pliku modelu .tflite
MODEL_PATH = os.path.join(os.path.dirname(os.path.dirname(__file__)), 'modele', 'model.tflite')


@app.route('/upload-data', methods=['POST'])
def upload_txt():
    try:
        # Sprawdź, czy w żądaniu znajduje się plik
        if 'file' not in request.files:
            return jsonify({"error": "No file part in the request"}), 400

        file = request.files['file']

        # Sprawdź, czy przesłano plik i czy ma odpowiedni format
        if file.filename == '':
            return jsonify({"error": "No file selected"}), 400

        if not file.filename.endswith('.txt'):
            return jsonify({"error": "File must be a .txt"}), 400

        # Odczytaj zawartość pliku
        content = file.read().decode('utf-8')
        print("Content of the uploaded file:")
        print(content)

        return jsonify({"message": "File uploaded successfully"}), 200

    except Exception as e:
        # Obsługa błędów
        return jsonify({"error": str(e)}), 500

@app.route('/download-model', methods=['GET'])
def download_model():
    try:
        # Sprawdź opcjonalne parametry (jeśli wymagane)
        client_info = request.args.get('client', 'unknown')
        print(f"Model requested by client: {client_info}")

        # Wysyłanie pliku modelu
        return send_file(
            MODEL_PATH,
            as_attachment=True,
            download_name="model.tflite",
            mimetype="application/octet-stream"
        )
    except Exception as e:
        # Obsługa błędów
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    # Uruchom serwer Flask
    app.run(host='0.0.0.0', port=5000)