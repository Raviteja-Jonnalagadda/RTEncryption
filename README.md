# RTEncryption

**RTEncryption** is a lightweight Java-based custom encryption and decryption utility.  
Unlike traditional AES or DES implementations, this library is designed with a **self-built encryption logic** (RTEncryption algorithm), making it ideal for learning, experimentation, or integrating with custom security flows.

---

## ✨ Features
- 🔐 Custom string encryption and decryption.
- 📦 Lightweight and dependency-free (pure Java).
- 🧩 Easy to integrate into existing projects.

---

## 🚀 Getting Started

### Prerequisites
- Java 8 or above
- Maven/Gradle (optional if packaging as a library)


### Usage
```import com.rtencryption.RTEncryption;
import com.rtencryption.RTEncryption;

public class Main {
    public static void main(String[] args) {
        String original = "HelloWorld123";
        String encrypted = RTEncryption.encrypt(original);
        String decrypted = RTEncryption.decrypt(encrypted);

        System.out.println("Original: " + original);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
```
### 📜 License

This project is licensed under the Apache License 2.0.
You are free to use, modify, and distribute this software under the terms of the license.

### Installation
Clone the repository:
```bash
git clone https://github.com/your-username/RTEncryption.git
```
### 👨‍💻 Author
```Raviteja Jonnalagadda 
📧 Email: raviteja032766@gmail.com
📞 Phone: +91 9346858141
🌍 Location: Visakhapatnam, Andhra Pradesh, India
```

⭐ Support

If you find this project useful, please consider giving it a star ⭐ on GitHub. It helps improve visibility and growth of the project.


