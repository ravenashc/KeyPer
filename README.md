KEYPER: AN IMPLEMENTATION OF AES-GCM FOR TEXT, FILE, AND FOLDER ENCRYPTION AND DECRYPTION.

The development and testing of KeyPer demonstrate that the integration of the Advanced Encryption Standard in Galois/Counter
Mode (AES-GCM) with a PBKDF2 key derivation function provides a highly robust and efficient solution for data security. Based
on the experimental results and implementation, the study concluded the following:
1. Security Integrity. The implementation of the 128-bit Authentication Tag within the GCM mode successfully fulfilled
the requirement for Authenticated Encryption (AEAD). Testing proved that the system reliably detects and blocks
tampered data (as seen in TC-C-005), ensuring both confidentiality and integrity.
2. Algorithmic Efficiency. The use of Password-Based Key Derivation Function 2 (PBKDF2) with 600,000 iterations
effectively "stretches" user passwords, making the application quantitatively resistant to brute-force attacks while
maintaining a user-friendly experience.
3. Data Structure Performance. Utilizing contiguous byte arrays for serialization and the java.nio.file.Path structure for
recursive traversal allowed for seamless handling of large amount of data. The inclusion of parallel processing in folder
operations significantly reduced computational time, proving that modern Java stream architectures are well-suited for
cryptographic tasks.
4. Practical Utility: The transition from low-level JCA APIs to a consolidated GUI-based tool successfully bridged the gap
between complex cryptographic theory and practical, general-purpose data protection for files and directories.
