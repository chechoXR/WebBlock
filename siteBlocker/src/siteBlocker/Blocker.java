package siteBlocker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Blocker extends Application{

	private static final String PATH_TEMP = System.getProperty("java.io.tmpdir");
	private static final String PATH = "C:/Windows/System32/drivers/etc/hosts";
	private static final int width  = 400;
	private static final int Height = 200;
	private static Scene sc;
	protected static final String password = "3183773922IAN";
	private static File host = new File(PATH);
//	private static File host = new File("/root/Escritorio/hosts");
	static String pth = "/root/Escritorio/hosts";
	
	public static void main(String[] args) throws  IOException {

		File f = new File (PATH_TEMP+"/Blocker.XR");
		
		
		if (!f.exists()) {
			Application.launch();

		}

		else
			System.exit(0);
	}

	@Override
	public void start(Stage st) throws Exception {

		
		BorderPane home = new BorderPane(); 
		home.setBackground(Background.EMPTY);
		
		File f = new File (PATH_TEMP+"/Blocker.XR");
		FileWriter fw = new FileWriter(f);
		
		inOut(home);
		sites(home);
		
		
		sc = new Scene(login(home,st));
		sc.setFill(Color.rgb( 210, 167, 255));
		st.setScene(sc);
		st.setResizable(false);

				
		
		st.show();

		st.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent ev) {
				try {
					fw.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				f.delete();
				
			}
		});
	}

	
	private static Pane login(BorderPane home, Stage st) {
		
		Pane p = new Pane();
		p.setMinSize(width, Height);
		PasswordField password = new PasswordField();
		password.setTranslateX(width/4);
		password.setTranslateY(Height/2);
		
		Button logIn = new Button("Entrar");
		logIn.setTranslateX(password.getTranslateX());
		logIn.setTranslateY(password.getTranslateY()+35);
		
		
		p.getChildren().addAll(password, logIn);
		
		logIn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
					if (password.getText().equals("") || !password.getText().equals(Blocker.password)) {
						Alert a = new Alert(AlertType.ERROR);
						a.setTitle("Error!");
						a.setContentText("Revisa los datos!");
						a.show();
					}
					else {
						sc.setRoot(home);
						st.sizeToScene();
					}
				
			}
		});
		
		
		
		return p;
		
	}
	
	private static void inOut(BorderPane home) {
		
		
		
		Pane p = new Pane();
		p.setMinSize(width, Height);
		p.setBackground(Background.EMPTY);
		home.setCenter(p);
		
		
		Label title = new Label("Web page Block!");
		title.setFont(Font.font(22));
		title.setTranslateX(width/4);
		
		Label pag = new Label("Página: ");
		pag.setFont(Font.font("18"));
		pag.setTranslateX(10);
		pag.setTranslateY(50);
		
		TextField pageIn = new TextField();
		pageIn.setTranslateX(60);
		pageIn.setTranslateY(47);
		
		
		
		Button add = new Button("Agregar");
		add.setStyle("-fx-background-color: #00ff49;");
		add.setTranslateX(240);
		add.setTranslateY(47);
		
		
		Button remove = new Button("Eliminar");
		remove.setStyle("-fx-background-color: #ff0000 ;");
		remove.setTranslateX(315);
		remove.setTranslateY(47);
		
		
		
		p.getChildren().addAll(title, pag, pageIn, add, remove);

		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				try {
					FileWriter fw = new FileWriter(host,true);
					BufferedWriter bw = new BufferedWriter(fw);
					if(pageIn.getText().trim().equals("")){
						Alert a = new Alert(AlertType.ERROR);
						a.setTitle("Error!");
						a.setContentText("El texto no debe ser vacio.");
						a.show();
						
					}
					else {
						bw.write("\n"+"127.0.0.1"+ "\t" + pageIn.getText().trim());
						pageIn.setText("");
					}
					bw.flush();
					bw.close();
					fw.close();
					sites(home);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		
		remove.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
					
				try {
										
					if(pageIn.getText().trim().equals("")){
						Alert a = new Alert(AlertType.ERROR);
						a.setTitle("Error!");
						a.setContentText("El texto no debe ser vacio.");
						a.show();
						pageIn.setText("");
					}
					else {
						FileReader fr = new FileReader(host);
						BufferedReader br = new BufferedReader(fr);
						
						File h2 = new File(PATH+"2");
						FileWriter fw = new FileWriter(h2);
						BufferedWriter bw = new BufferedWriter(fw);
						
						String read;
						
						while((read = br.readLine())!=(null)) {
							if(!("127.0.0.1"+ "\t" +pageIn.getText().trim()).equals(read))
								bw.write(read+"\n");
						}
						pageIn.setText("");
						host.delete();
						h2.renameTo(host);
						host = new File(PATH);
						br.close();
						bw.flush();
						bw.close();
						fw.close();
						pageIn.setText("");
						sites(home);
						
						File borrar = new File(PATH+"2");
						if(borrar.exists())
							borrar.delete();
						
						
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				
			
			}	
		});
		
	}
	
	
	private static void sites(BorderPane home) throws IOException {
		
		VBox p = new VBox();
		ScrollPane sites = new ScrollPane();
		sites.setMaxSize(width, Height);
		
		p.setStyle	  ("-fx-background-color: white;");
		sites.setStyle("-fx-background-color: transparent;");
		home.setBottom(sites);
		
		
		try {
			FileReader fr = new FileReader(host);
			BufferedReader br = new BufferedReader(fr);
			String line=br.readLine();
			int Cont=0;
			StringTokenizer st;
			while(line != null)
			{
				if(line.length()>0)
				if(line.charAt(0)!='#')
				{
					st = new StringTokenizer(line,"\t");
					st.nextToken();
					Label l = new Label(st.nextToken());
					p.getChildren().add(l);
					Cont++;
				}
				
				line = br.readLine();
			}
			if(Cont==0)
				p.getChildren().add(new Label("No hay sitios bloqueados aun."));
			sites.setContent(p);
			br.close();
		} catch (FileNotFoundException e) {
	
			System.err.println("No hay archivo!");
			System.exit(0);
		}
		
		
	}
	
}
