import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MiyuConversationFlow {
    private Map<String, String> responses;
    private String lastUserInput;

    public MiyuConversationFlow() {
        responses = new HashMap<>();
        initializeResponses();
    }

    private void initializeResponses() {
        responses.put("hello", "Hi there! How can I help you today?");
        responses.put("how are you", "I'm just a chatbot, but I'm feeling great! How about you?");
        responses.put("what is your name", "I'm Miyu Nakamura, your AI assistant.");
        responses.put("what can you do", "I can chat with you, help with tasks, and learn from our conversations!");
        responses.put("thank you", "You're welcome! 😊");
        responses.put("goodbye", "Goodbye! Have a great day! 💙");
    }

    public String getResponse(String input) {
        input = input.toLowerCase().trim();
        lastUserInput = input;

        if (responses.containsKey(input)) {
            return responses.get(input);
        } else if (input.contains("help")) {
            return "I can assist you with general questions. Try asking 'what can you do'!";
        } else if (lastUserInput != null && input.equals("why")) {
            return "That's a great question! Can you tell me more about what you mean?";
        }
        return "I'm not sure about that. Could you rephrase or provide more details?";
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Miyu: Hello! Let's chat. Type 'exit' to quit.");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine().trim();
            
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Miyu: Goodbye! It was nice talking to you.");
                break;
            }
            
            String response = getResponse(userInput);
            System.out.println("Miyu: " + response);
        }
        scanner.close();
    }

    public static void main(String[] args) {
        MiyuConversationFlow miyu = new MiyuConversationFlow();
        miyu.startChat();
    }
}
