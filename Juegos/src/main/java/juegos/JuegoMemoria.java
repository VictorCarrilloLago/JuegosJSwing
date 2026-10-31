package juegos;

import java.util.Random;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license Click
 * nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this
 * template
 *
 *
 * @author dam
 */
public class JuegoMemoria extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	ImageIcon pikachu = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Pikachu.png"));
	ImageIcon bulbasaur = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Bulbasaur.png"));
	ImageIcon charmander = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Charmander.png"));
	ImageIcon squirtle = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Squirtle.png"));
	ImageIcon mew = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Mew.png"));
	ImageIcon snorlax = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Snorlax.png"));
	ImageIcon gengar = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Gengar.png"));
	ImageIcon gyarados = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Gyarados.png"));
	ImageIcon arcanine = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Arcanine.png"));
	ImageIcon umbreon = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Umbreon.png"));
	ImageIcon alakazam = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Alakazam.png"));
	ImageIcon machamp = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Machamp.png"));
	ImageIcon eevee = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Eevee.png"));
	ImageIcon lapras = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Lapras.png"));
	ImageIcon psyduck = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Psyduck.png"));
	ImageIcon jigglypluff = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Jigglypuff.png"));
	ImageIcon cubone = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Cubone.png"));
	ImageIcon onyx = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Onyx.png"));
	ImageIcon pregunta = new ImageIcon(JuegoMemoria.class.getResource("/multimedia/Pregunta.png"));

	private final Icon[] imagenes = { pikachu, bulbasaur, charmander, squirtle, mew, snorlax, gengar, gyarados,
			arcanine, umbreon, alakazam, machamp, eevee, lapras, psyduck, jigglypluff, cubone, onyx, pikachu, bulbasaur,
			charmander, squirtle, mew, snorlax, gengar, gyarados, arcanine, umbreon, alakazam, machamp, eevee, lapras,
			psyduck, jigglypluff, cubone, onyx };

	private int[] estado = new int[36];
	private static final Random rd = new Random();
	private static int botonesPulsados = 0;
	private static int botonPulsado = 0;
	private static int turno = 0;
	private int[] puntos = new int[2];
	private int[] ganadas = new int[2];
	private JButton[] botones;
	private boolean bloqueado = false;

	public JuegoMemoria() {
		initComponents();
		inicializarBotones();
		darImagenes();
		Reiniciar.setEnabled(false);
	}

	public final void inicializarBotones() {
		botones = new JButton[] { Boton1, Boton2, Boton3, Boton4, Boton5, Boton6, Boton7, Boton8, Boton9, Boton10,
				Boton11, Boton12, Boton13, Boton14, Boton15, Boton16, Boton17, Boton18, Boton19, Boton20, Boton21,
				Boton22, Boton23, Boton24, Boton25, Boton26, Boton27, Boton28, Boton29, Boton30, Boton31, Boton32,
				Boton33, Boton34, Boton35, Boton36 };

		for (JButton boton : botones) {
			boton.setIcon(pregunta);
		}
	}

	public final void darImagenes() {
		for (int i = 0; i < estado.length; i++) {
			estado[i] = -1;
		}

		for (int i = 0; i < estado.length; i++) {
			int contador = 0;
			int random = rd.nextInt(16);
			while (contador < 35) {
				if (random == estado[contador]) {
					random = rd.nextInt(36);
					contador = 0;
				} else {
					contador++;
				}
			}
			estado[i] = random;
		}

	}

	public void descubrirFicha(JButton boton, int posBoton) {
		if (bloqueado) {
			return;
		}
		if (!boton.getIcon().equals(pregunta)) {
			return;
		}
		boton.setIcon(imagenes[estado[posBoton]]);
		botonesPulsados++;
		if (botonesPulsados == 2) {
			boolean acertado = iguales(posBoton, botonPulsado);
			if (acertado) {
				puntos[turno]++;
				if (turno == 0) {
					PuntosJ1.setText("Puntos: " + puntos[0]);
				} else {
					PuntosJ2.setText("Puntos: " + puntos[1]);
				}
				botonesPulsados = 0;
			} else {
				bloqueado = true;
				JButton botonAnterior = botones[botonPulsado];
				Timer timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						boton.setIcon(pregunta);
						botonAnterior.setIcon(pregunta);
						botonesPulsados = 0;
						bloqueado = false;
						if (turno == 0) {
							turno++;
						} else {
							turno = 0;
						}
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
		} else {
			botonPulsado = posBoton;
		}
		comprobarGanador();
	}

	public boolean iguales(int pos1, int pos2) {
		int valor1 = estado[pos1];
		int valor2 = estado[pos2];

		return valor1 % 18 == valor2 % 18;
	}

	public void comprobarGanador() {
		int total = puntos[0] + puntos[1];
		if (total == 18) {
			if (puntos[0] > puntos[1]) {
				JOptionPane.showMessageDialog(this,
						"VICTORIA DEL JUGADOR 1, CON UN " + puntos[0] + " A " + puntos[1] + ". ENHORABUENA");
				ganadas[0]++;
				PartidasJ1.setText("Partidas: " + ganadas[0]);
			} else if (puntos[0] < puntos[1]) {
				JOptionPane.showMessageDialog(this,
						"VICTORIA DEL JUGADOR 2, CON UN " + puntos[1] + " A " + puntos[0] + ". ENHORABUENA");
				ganadas[1]++;
				PartidasJ2.setText("Partidas: " + ganadas[1]);
			} else {
				JOptionPane.showMessageDialog(this, "LA PARTIDA ACABÓ EN EMPATE.");
			}
			if (ganadas[0] == 2) {
				JOptionPane.showMessageDialog(this, "VICTORIA GLOBAL DEL JUGADOR 1. BUENA PARTIDA.");
				System.exit(0);
			} else if (ganadas[1] == 2) {
				JOptionPane.showMessageDialog(this, "VICTORIA GLOBAL DEL JUGADOR 2. BUENA PARTIDA.");
				System.exit(0);
			}
			Reiniciar.setEnabled(true);
		}
	}

	public void reiniciar() {
		puntos[0] = 0;
		puntos[1] = 0;
		PuntosJ1.setText("Puntos: " + puntos[0]);
		PuntosJ2.setText("Puntos: " + puntos[1]);
		inicializarBotones();
		darImagenes();
		Reiniciar.setEnabled(false);
		turno = 0;
		botonPulsado = 0;
		botonesPulsados = 0;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		J1 = new javax.swing.JLabel();
		J2 = new javax.swing.JLabel();
		PartidasJ2 = new javax.swing.JLabel();
		PartidasJ1 = new javax.swing.JLabel();
		Reiniciar = new javax.swing.JButton();
		PuntosJ1 = new javax.swing.JLabel();
		PuntosJ2 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		Boton1 = new javax.swing.JButton();
		Boton2 = new javax.swing.JButton();
		Boton3 = new javax.swing.JButton();
		Boton4 = new javax.swing.JButton();
		Boton5 = new javax.swing.JButton();
		Boton6 = new javax.swing.JButton();
		Boton7 = new javax.swing.JButton();
		Boton8 = new javax.swing.JButton();
		Boton9 = new javax.swing.JButton();
		Boton10 = new javax.swing.JButton();
		Boton11 = new javax.swing.JButton();
		Boton12 = new javax.swing.JButton();
		Boton13 = new javax.swing.JButton();
		Boton14 = new javax.swing.JButton();
		Boton15 = new javax.swing.JButton();
		Boton16 = new javax.swing.JButton();
		Boton17 = new javax.swing.JButton();
		Boton18 = new javax.swing.JButton();
		Boton19 = new javax.swing.JButton();
		Boton20 = new javax.swing.JButton();
		Boton21 = new javax.swing.JButton();
		Boton22 = new javax.swing.JButton();
		Boton23 = new javax.swing.JButton();
		Boton24 = new javax.swing.JButton();
		Boton25 = new javax.swing.JButton();
		Boton26 = new javax.swing.JButton();
		Boton27 = new javax.swing.JButton();
		Boton28 = new javax.swing.JButton();
		Boton29 = new javax.swing.JButton();
		Boton30 = new javax.swing.JButton();
		Boton31 = new javax.swing.JButton();
		Boton32 = new javax.swing.JButton();
		Boton33 = new javax.swing.JButton();
		Boton34 = new javax.swing.JButton();
		Boton35 = new javax.swing.JButton();
		Boton36 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(850, 950));

		J1.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
		J1.setForeground(new java.awt.Color(0, 0, 255));
		J1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		J1.setText("JUGADOR 1");

		J2.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
		J2.setForeground(new java.awt.Color(255, 0, 0));
		J2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		J2.setText("JUGADOR 2");

		PartidasJ2.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
		PartidasJ2.setForeground(new java.awt.Color(255, 0, 0));
		PartidasJ2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		PartidasJ2.setText("Partidas: 0");

		PartidasJ1.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
		PartidasJ1.setForeground(new java.awt.Color(0, 0, 255));
		PartidasJ1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		PartidasJ1.setText("Partidas: 0");

		Reiniciar.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
		Reiniciar.setText("Reiniciar");
		Reiniciar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ReiniciarActionPerformed(evt);
			}
		});

		PuntosJ1.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
		PuntosJ1.setForeground(new java.awt.Color(0, 0, 255));
		PuntosJ1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		PuntosJ1.setText("Puntos: 0");

		PuntosJ2.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
		PuntosJ2.setForeground(new java.awt.Color(255, 0, 0));
		PuntosJ2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		PuntosJ2.setText("Puntos: 0");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(57, 57, 57).addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(PartidasJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(PuntosJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(140, 140, 140)
												.addComponent(Reiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 137,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(PartidasJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 185,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(386, 386, 386)
												.addComponent(PuntosJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 185,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(52, 52, 52))
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addComponent(J1, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(J2, javax.swing.GroupLayout.PREFERRED_SIZE, 185,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(52, 52, 52)))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(J1).addComponent(J2))
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(PartidasJ2).addComponent(PartidasJ1)
								.addComponent(Reiniciar, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(PuntosJ1).addComponent(PuntosJ2))
						.addContainerGap(12, Short.MAX_VALUE)));

		getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

		jPanel2.setLayout(new java.awt.GridLayout(6, 6));

		Boton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton1ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton1);

		Boton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton2ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton2);

		Boton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton3ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton3);

		Boton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton4ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton4);

		Boton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton5ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton5);

		Boton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton6ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton6);

		Boton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton7ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton7);

		Boton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton8ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton8);

		Boton9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton9ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton9);

		Boton10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton10ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton10);

		Boton11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton11ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton11);

		Boton12.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton12ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton12);

		Boton13.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton13ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton13);

		Boton14.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton14ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton14);

		Boton15.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton15ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton15);

		Boton16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton16ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton16);

		Boton17.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton17ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton17);

		Boton18.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton18ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton18);

		Boton19.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton19ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton19);

		Boton20.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton20ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton20);

		Boton21.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton21ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton21);

		Boton22.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton22ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton22);

		Boton23.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton23ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton23);

		Boton24.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton24ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton24);

		Boton25.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton25ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton25);

		Boton26.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton26ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton26);

		Boton27.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton27ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton27);

		Boton28.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton28ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton28);

		Boton29.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton29ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton29);

		Boton30.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton30ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton30);

		Boton31.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton31ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton31);

		Boton32.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton32ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton32);

		Boton33.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton33ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton33);

		Boton34.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton34ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton34);

		Boton35.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton35ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton35);

		Boton36.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Boton36ActionPerformed(evt);
			}
		});
		jPanel2.add(Boton36);

		getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void Boton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton1ActionPerformed
		descubrirFicha(Boton1, 0);
	}// GEN-LAST:event_Boton1ActionPerformed

	private void Boton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton2ActionPerformed
		descubrirFicha(Boton2, 1);
	}// GEN-LAST:event_Boton2ActionPerformed

	private void Boton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton3ActionPerformed
		descubrirFicha(Boton3, 2);
	}// GEN-LAST:event_Boton3ActionPerformed

	private void Boton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton4ActionPerformed
		descubrirFicha(Boton4, 3);
	}// GEN-LAST:event_Boton4ActionPerformed

	private void Boton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton5ActionPerformed
		descubrirFicha(Boton5, 4);
	}// GEN-LAST:event_Boton5ActionPerformed

	private void Boton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton6ActionPerformed
		descubrirFicha(Boton6, 5);
	}// GEN-LAST:event_Boton6ActionPerformed

	private void Boton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton7ActionPerformed
		descubrirFicha(Boton7, 6);
	}// GEN-LAST:event_Boton7ActionPerformed

	private void Boton8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton8ActionPerformed
		descubrirFicha(Boton8, 7);
	}// GEN-LAST:event_Boton8ActionPerformed

	private void Boton9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton9ActionPerformed
		descubrirFicha(Boton9, 8);
	}// GEN-LAST:event_Boton9ActionPerformed

	private void Boton10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton10ActionPerformed
		descubrirFicha(Boton10, 9);
	}// GEN-LAST:event_Boton10ActionPerformed

	private void Boton11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton11ActionPerformed
		descubrirFicha(Boton11, 10);
	}// GEN-LAST:event_Boton11ActionPerformed

	private void Boton12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton12ActionPerformed
		descubrirFicha(Boton12, 11);
	}// GEN-LAST:event_Boton12ActionPerformed

	private void Boton13ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton13ActionPerformed
		descubrirFicha(Boton13, 12);
	}// GEN-LAST:event_Boton13ActionPerformed

	private void Boton14ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton14ActionPerformed
		descubrirFicha(Boton14, 13);
	}// GEN-LAST:event_Boton14ActionPerformed

	private void Boton15ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton15ActionPerformed
		descubrirFicha(Boton15, 14);
	}// GEN-LAST:event_Boton15ActionPerformed

	private void Boton16ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton16ActionPerformed
		descubrirFicha(Boton16, 15);
	}// GEN-LAST:event_Boton16ActionPerformed

	private void Boton17ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton17ActionPerformed
		descubrirFicha(Boton17, 16);
	}// GEN-LAST:event_Boton17ActionPerformed

	private void Boton18ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton18ActionPerformed
		descubrirFicha(Boton18, 17);
	}// GEN-LAST:event_Boton18ActionPerformed

	private void Boton19ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton19ActionPerformed
		descubrirFicha(Boton19, 18);
	}// GEN-LAST:event_Boton19ActionPerformed

	private void Boton20ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton20ActionPerformed
		descubrirFicha(Boton20, 19);
	}// GEN-LAST:event_Boton20ActionPerformed

	private void Boton21ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton21ActionPerformed
		descubrirFicha(Boton21, 20);
	}// GEN-LAST:event_Boton21ActionPerformed

	private void Boton22ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton22ActionPerformed
		descubrirFicha(Boton22, 21);
	}// GEN-LAST:event_Boton22ActionPerformed

	private void Boton23ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton23ActionPerformed
		descubrirFicha(Boton23, 22);
	}// GEN-LAST:event_Boton23ActionPerformed

	private void Boton24ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton24ActionPerformed
		descubrirFicha(Boton24, 23);
	}// GEN-LAST:event_Boton24ActionPerformed

	private void Boton25ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton25ActionPerformed
		descubrirFicha(Boton25, 24);
	}// GEN-LAST:event_Boton25ActionPerformed

	private void Boton26ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton26ActionPerformed
		descubrirFicha(Boton26, 25);
	}// GEN-LAST:event_Boton26ActionPerformed

	private void Boton27ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton27ActionPerformed
		descubrirFicha(Boton27, 26);
	}// GEN-LAST:event_Boton27ActionPerformed

	private void Boton28ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton28ActionPerformed
		descubrirFicha(Boton28, 27);
	}// GEN-LAST:event_Boton28ActionPerformed

	private void Boton29ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton29ActionPerformed
		descubrirFicha(Boton29, 28);
	}// GEN-LAST:event_Boton29ActionPerformed

	private void Boton30ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton30ActionPerformed
		descubrirFicha(Boton30, 29);
	}// GEN-LAST:event_Boton30ActionPerformed

	private void Boton31ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton31ActionPerformed
		descubrirFicha(Boton31, 30);
	}// GEN-LAST:event_Boton31ActionPerformed

	private void Boton32ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton32ActionPerformed
		descubrirFicha(Boton32, 31);
	}// GEN-LAST:event_Boton32ActionPerformed

	private void Boton33ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton33ActionPerformed
		descubrirFicha(Boton33, 32);
	}// GEN-LAST:event_Boton33ActionPerformed

	private void Boton34ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton34ActionPerformed
		descubrirFicha(Boton34, 33);
	}// GEN-LAST:event_Boton34ActionPerformed

	private void Boton35ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton35ActionPerformed
		descubrirFicha(Boton35, 34);
	}// GEN-LAST:event_Boton35ActionPerformed

	private void Boton36ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Boton36ActionPerformed
		descubrirFicha(Boton36, 35);
	}// GEN-LAST:event_Boton36ActionPerformed

	private void ReiniciarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ReiniciarActionPerformed
		reiniciar();
	}// GEN-LAST:event_ReiniciarActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(JuegoMemoria.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(JuegoMemoria.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(JuegoMemoria.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JuegoMemoria.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new JuegoMemoria().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton Boton1;
	private javax.swing.JButton Boton10;
	private javax.swing.JButton Boton11;
	private javax.swing.JButton Boton12;
	private javax.swing.JButton Boton13;
	private javax.swing.JButton Boton14;
	private javax.swing.JButton Boton15;
	private javax.swing.JButton Boton16;
	private javax.swing.JButton Boton17;
	private javax.swing.JButton Boton18;
	private javax.swing.JButton Boton19;
	private javax.swing.JButton Boton2;
	private javax.swing.JButton Boton20;
	private javax.swing.JButton Boton21;
	private javax.swing.JButton Boton22;
	private javax.swing.JButton Boton23;
	private javax.swing.JButton Boton24;
	private javax.swing.JButton Boton25;
	private javax.swing.JButton Boton26;
	private javax.swing.JButton Boton27;
	private javax.swing.JButton Boton28;
	private javax.swing.JButton Boton29;
	private javax.swing.JButton Boton3;
	private javax.swing.JButton Boton30;
	private javax.swing.JButton Boton31;
	private javax.swing.JButton Boton32;
	private javax.swing.JButton Boton33;
	private javax.swing.JButton Boton34;
	private javax.swing.JButton Boton35;
	private javax.swing.JButton Boton36;
	private javax.swing.JButton Boton4;
	private javax.swing.JButton Boton5;
	private javax.swing.JButton Boton6;
	private javax.swing.JButton Boton7;
	private javax.swing.JButton Boton8;
	private javax.swing.JButton Boton9;
	private javax.swing.JLabel J1;
	private javax.swing.JLabel J2;
	private javax.swing.JLabel PartidasJ1;
	private javax.swing.JLabel PartidasJ2;
	private javax.swing.JLabel PuntosJ1;
	private javax.swing.JLabel PuntosJ2;
	private javax.swing.JButton Reiniciar;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	// End of variables declaration//GEN-END:variables
}
