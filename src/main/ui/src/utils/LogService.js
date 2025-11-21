class LogService {
    static apiEndpoint = '/api/logs';

    // Send log to server with retry logic
    static async sendLog(message, level = 'INFO') {
        const logData = {
            message: message,
            level: level,
            timestamp: new Date().toISOString()
        };

        try {
            const response = await fetch(this.apiEndpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(logData),
            });

            if (!response.ok) {
                throw new Error('Failed to send log');
            }

            console.log('Log sent successfully.');
        } catch (error) {
            console.error('Error sending log to server:', error);
        }
    }
}

export default LogService;
