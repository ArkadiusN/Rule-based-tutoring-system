package main;

import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.Auditor;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import main.BinarySearchTree;

import javax.swing.*; //Many components use thus "*" used.
import java.awt.*;    //Many components use thus "*" used.
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Implementation of Rule-based tutoring system.
 * @author Arkadiusz Nowacki (anowa002)
 * @version openjdk-17 java version "17.0.1", maven compiler source 14
 */
public class ClientMenu extends JFrame{
    // Swing library variables
    JTextArea questionArea;
    JTextArea optionArea;
    JTextArea scoreArea;
    JTextArea ruleBindingsArea;
    JTextField textField;
    JPanel panel;
    JButton button;
    // Colors used
    Color [] themes = new Color[]{
            new Color(255,255,255),
            new Color(25,25,25),
            new Color(249, 89,89),
            new Color(50,50,50)};

    // Variables
    List<Question> questionsList = new ArrayList<>();
    int index = 0;
    RuleBook ruleBook;
    NameValueReferableMap factMap = new FactMap(); // Needs to be here to update the score correctly.
    ArrayList<String> bindingsList = new ArrayList<>();
    /* Width (x) and height (y) for quicker centering of JFrame */
    int x = 650, y = 750;

    /**
     * Graphical suer interface displayed once the program runs.
     * It displays the section such as: questions, options, input field
     * and submit button.
     */
    protected ClientMenu() {
        /* Setting up the JFrame */
        setTitle("Rule-based tutoring system");
        setSize(x,y);
        getContentPane().setBackground(themes[3]);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        /* JFrame appears on the centre of our desktop (using this x and y values) */
        setLocation(dim.width/2-this.getPreferredSize().width/2 - x/2, dim.height/2-this.getPreferredSize().height/2  -y/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        /* Component's initialization */
        questionArea = new JTextArea();
        optionArea = new JTextArea();
        scoreArea = new JTextArea();
        textField = new JTextField();
        panel = new JPanel();
        button = new JButton("<html><h1>" + "Start the quiz" + "</h1></html>");

        /* Setting up each frame component */
        questionArea.setBounds(0,0,650,130);
        questionArea.setLineWrap(true);
        questionArea.setBackground(themes[1]);
        questionArea.setForeground(new Color(25,255,0));
        questionArea.setFont(new Font("Helvetica", Font.BOLD, 20));
        questionArea.setMargin( new Insets(0,60,0,60));

        optionArea.setBounds(0,130,650,150);
        optionArea.setLineWrap(true);
        optionArea.setBackground(themes[3]);
        optionArea.setForeground(themes[0]);
        optionArea.setFont(new Font("Helvetica", Font.BOLD, 17));
        optionArea.setMargin( new Insets(0,60,0,0));

        scoreArea.setBounds(0,280,650,150);
        scoreArea.setLineWrap(true);
        scoreArea.setBackground(themes[3]);
        scoreArea.setForeground(themes[0]);
        scoreArea.setFont(new Font("Helvetica", Font.BOLD, 17));
        scoreArea.setMargin( new Insets(0,60,0,0));

        textField.setBounds(x/2 - 250/2,550,250,50);
        textField.setFont(new Font("Helvetica", Font.BOLD, 20));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBackground(themes[0]);
        textField.setForeground(themes[1]);

        /* Change the edit ability and visibility */
        questionArea.setEditable(false);
        optionArea.setEditable(false);
        scoreArea.setEditable(false);
        textField.setBorder(null);
        textField.setEnabled(false);
        textField.setVisible(false);

        panel.setBackground(themes[1]);
        panel.setBounds(0,600,650,200);
        panel.setLayout(new FlowLayout());

        button.setPreferredSize(new Dimension(250, 100));

        /* Load the data required which is
           questions and options */
        getQuestionAndOptions();

        /* Action Listener as Lambda expressions */
        button.addActionListener(e -> {
            afterStart();
            textField.setEnabled(true);
            if(e.getSource().equals(button)) {
                controlMechanismAlgorithm();
                nextQuestion();
                index++;
            }
        });

        /* Add JTextAreas */
        add(questionArea);
        add(optionArea);
        add(scoreArea);

        /* Add JField & JPanel with its components*/
        add(textField);
        add(panel);
        panel.add(button);

        /* Set visibility and display data once system runs*/
        setVisible(true);
    }

    /**
     * A display of each question from the "questionList" called in the TextAreas
     * designated for it.
     */
    private void nextQuestion(){
        int choicesSplit = 0;
        if(index >= questionsList.size()){
            displayFeedback();
        }else {
            String question = questionsList.get(index).getAnswerChoices();
            String [] split = question.split("[¬]");
            questionArea.setText("main.Question " + index + ".\n" + questionsList.get(index).getQuestions());
            while (choicesSplit != split.length) {
                optionArea.append(split[choicesSplit] +"\n");
                choicesSplit++;
            }
        }
    }

    /**
     * After the start quiz button is clicked change the text inside button
     * and display the text field where user can provide answers.
     */
    private void afterStart(){
        questionArea.setText("");
        optionArea.setText("");
        button.setText("<html><h1>" + "Submit the answer" + "</h1></html>");
        textField.setEnabled(true);
        textField.setVisible(true);
    }

    /**
     * Display a feedback in a form o the tree based on the feedback given from
     * the algorithm.
     */
    private void displayFeedback(){
        /* Set up a message for feedback window */
        JTextField feedbackLabel = new JTextField();
        feedbackLabel.setBounds(0,0,650, 100);
        feedbackLabel.setText("The results of the quiz");
        feedbackLabel.setHorizontalAlignment(JTextField.CENTER);
        feedbackLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        feedbackLabel.setForeground(new Color(25,255,0));
        feedbackLabel.setBackground(themes[1]);
        feedbackLabel.setBorder(null);

        /* Settings for the feedback area */
        ruleBindingsArea = new JTextArea();
        ruleBindingsArea.setVisible(false);
        ruleBindingsArea.setBounds(0,100,650,600);
        ruleBindingsArea.setLineWrap(true);
        ruleBindingsArea.setBackground(themes[1]);
        ruleBindingsArea.setForeground(themes[0]);
        ruleBindingsArea.setFont(new Font("Helvetica", Font.BOLD, 10));
        ruleBindingsArea.setEditable(false);
        ruleBindingsArea.setMargin(new Insets(0,20,0,20));

        /* Add all rule bindings to the feedback window */
        int counter = 0;
        for (String s : bindingsList) {
            ruleBindingsArea.append(s + "\n");
            counter++;
        }

        /* Change the text of the score area at the end */
        scoreArea.setText("Total score is: " + (counter * 5));
        scoreArea.setBounds(0,600,650,200);
        scoreArea.setFont(new Font("Helvetica", Font.BOLD, 20));

        /* Add JLabel and JTextArea */
        add(feedbackLabel);
        add(ruleBindingsArea);

        /* Enable and disable components in the end feedback section */
        button.setEnabled(false);
        button.setVisible(false);
        questionArea.setVisible(false);
        optionArea.setVisible(false);
        scoreArea.setVisible(true);
        textField.setVisible(false);
        ruleBindingsArea.setVisible(true);
    }

    /**
     * Getting the questions and the options from the files called "questions.txt"
     * and "options.txt". Storing it in the container for later use.
     */
    private void getQuestionAndOptions(){
        try {
            /* Read the files and put data into the list */
            FileInputStream dictQue = new FileInputStream("questions.txt");
            FileInputStream dictOpt = new FileInputStream("options.txt");
            Scanner in = new Scanner(dictQue);
            Scanner in2 = new Scanner(dictOpt);
            while (in.hasNextLine() && in2.hasNextLine()){
                questionsList.add(new Question(in.nextLine(), in2.nextLine()));
            }
            in.close();
        }catch (FileNotFoundException err){
            System.out.println("The error is: " + err);
        }
    }

    /**
     * The control mechanism (or inference engine) that collects information
     * from the user, adapts this information to lastly send it through the rules
     * to match the assertions to correct rules. If any rule matches it leads to a
     * status of "Executed" and the Action is taken as it is defined in the rule.
     * Binary search tree saves all rules fired and saves their "Bindings".
     * The cyclic process repeats as long as there are questions available.
     */
    private void controlMechanismAlgorithm(){
        /* Save the "Rule bindings" based on the
           rules that were executed */
        ArrayList<Integer> rulesExecuted = new ArrayList<>();
        BinarySearchTree ruleBindingsBST = new BinarySearchTree();
        String answer;
        answer = textField.getText();
        if(index > questionsList.size()) {
            System.out.println("Questions list was not filled");
        }else {
            factMap.put(new Fact("F" + index, answer));
            System.out.println("Index: " + index);
            System.out.println("Our fact: " + "F" + index);
            System.out.println("Our answer: " + answer);
        }
        rules();
        ruleBook.run(factMap);
        Optional scoreFromRules = ruleBook.getResult();
        String scoreFromRuleString = scoreFromRules.get().toString();
        scoreArea.setText("Current score is: " + scoreFromRuleString);
        scoreArea.setFont(new Font("Helvetica", Font.BOLD, 20));
        Auditor our_auditor = (Auditor) ruleBook;
        for (int i = 1; i <= our_auditor.getRuleStatusMap().size() ; i++) {
            if(our_auditor.getRuleStatus("Rule" + i).toString().equals("EXECUTED")){
                rulesExecuted.add(i);
            }
        }

        /* Save the rules of status "EXECUTED" into BST */
        for (Integer integer : rulesExecuted) {
            ruleBindingsBST.insert(integer);
        }

        /* Save the bindings into a String list after using
        the tree structure format from "inOrder()" method. */
        bindingsList = ruleBindingsBST.inOrder();
    }

    /**
     * A set of defined rules for the questions.
     */
    private void rules(){
        try {
            /* Uses a rule chaining to reduce
            the possibility of errors on loading or exceptions
            thrown while rules are running.
            Now it is only possible to stop running when rule evaluate to
            true which is the result expected*/
            final int fiveP = 5;
            ruleBook = RuleBookBuilder
                    .create()
                    .withResultType(Integer.class)
                    .withDefaultResult(0)
                    .asAuditor()
                    // Searching Algorithms Section.
                    .addRule(rule -> rule
                            .withName("Rule1")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F1").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule2")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F2").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule3")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F3").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule4")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F4").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule5")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F5").equals("4"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule6")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F6").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule7")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F7").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule8")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F8").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule9")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F9").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())

                    // Recursion section.
                    .addRule(rule -> rule
                            .withName("Rule10")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F10").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule11")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F11").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule12")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F12").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule13")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F13").equals("4"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule14")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F14").equals("5"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule15")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F15").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule16")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F16").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule17")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F17").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule18")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F18").equals("4"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())

                    // Sorting Algorithms section.
                    .addRule(rule -> rule
                            .withName("Rule19")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F19").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule20")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F20").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule21")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F21").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule22")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F22").equals("4"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule23")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F23").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule24")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F24").equals("4"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule25")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F25").equals("4"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule26")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F26").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule27")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F27").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule28")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F28").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule29")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F29").equals("5"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule30")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("30").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule31")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F31").equals("3"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule32")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F32").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule33")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F33").equals("1"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule34")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F34").equals("2"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .addRule(rule -> rule
                            .withName("Rule35")
                            .withNoSpecifiedFactType()
                            .when(f -> f.getStrVal("F35").equals("4"))
                            .then((facts, result) -> result.setValue(result.getValue() + fiveP))
                            .then((facts, result) -> result.getValue())
                            .build())
                    .build();
        }catch (NullPointerException err) {
            System.out.println("The error is: " + err);
        }
    }
}// End of class
