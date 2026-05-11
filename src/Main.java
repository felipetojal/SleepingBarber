import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Barbearia Aberta ---");
        System.out.println("Capacidade da fila: 10 cadeiras.");

        BlockingQueue<Integer> salaDeEspera = new ArrayBlockingQueue<>(10);

        // Instanciando as threads dos 2 barbeiros
        Thread barbeiro1 = new Thread(new Barbeiro("Barbeiro 1", salaDeEspera));
        Thread barbeiro2 = new Thread(new Barbeiro("Barbeiro 2", salaDeEspera));

        // Instanciando a thread que gera os clientes
        Thread geradorDeClientes = new Thread(new GeradorClientes(salaDeEspera));

        // Iniciando o expediente
        barbeiro1.start();
        barbeiro2.start();
        geradorDeClientes.start();
    }
}

class Barbeiro implements Runnable {
    private final String nome;
    private final BlockingQueue<Integer> salaDeEspera;
    private final Random random = new Random();

    public Barbeiro(String nome, BlockingQueue<Integer> salaDeEspera) {
        this.nome = nome;
        this.salaDeEspera = salaDeEspera;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(nome + " está dormindo/esperando por clientes.");
                Integer idCliente = salaDeEspera.take();

                System.out.println("-> " + nome + " acordou e começou a atender o Cliente " + idCliente);

                // Simula o tempo de corte entre 5s e 15s
                int tempoDeCorte = 5 + random.nextInt(11);
                Thread.sleep(tempoDeCorte * 1000L);

                System.out.println("<- " + nome + " terminou o Cliente " + idCliente + " em " + tempoDeCorte + " segundos.");
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " encerrou o expediente.");
            Thread.currentThread().interrupt();
        }
    }
}

class GeradorClientes implements Runnable {
    private final BlockingQueue<Integer> salaDeEspera;
    private final Random random = new Random();
    private int contadorClientes = 1;

    public GeradorClientes(BlockingQueue<Integer> salaDeEspera) {
        this.salaDeEspera = salaDeEspera;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Simula o intervalo de chegada de novos clientes entre 4s e 6s
                int tempoChegada = 4 + random.nextInt(3);
                Thread.sleep(tempoChegada * 1000L);

                int idCliente = contadorClientes++;
                System.out.println("[ENTRADA] Cliente " + idCliente + " chegou na barbearia.");

                // Tenta colocar na fila. Retorna true se houver espaço, false se estiver cheia.
                if (salaDeEspera.offer(idCliente)) {
                    System.out.println("    Cliente " + idCliente + " sentou na sala de espera. (Fila: " + salaDeEspera.size() + "/10)");
                } else {
                    System.out.println("    Cliente " + idCliente + " foi embora! A fila está CHEIA (10 clientes).");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}