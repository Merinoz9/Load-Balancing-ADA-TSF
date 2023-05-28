package mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid;

import java.util.List;
import java.util.UUID;

import de.irf.it.rmg.core.teikoku.job.Job;
import de.irf.it.rmg.core.teikoku.workload.job.SWFJob;
import mx.cicese.dcc.teikoku.scheduler.plan.AllocationEntry;
import mx.cicese.dcc.teikoku.scheduler.plan.JobControlBlock;

public class LP extends RigidStgy {

    /**
     * Class constructor
     */
    public LP() {
        super();
    }

    /**
     * (non-Javadoc)
     *
     * @see mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid.RigidStgy#schedule(Job
     *      job)
     */
    public AllocationEntry schedule(Job job, JobControlBlock jcb) {
        // GET THE LIST OF MACHINES IN THE SYSTEM
        if (jobFilteringStgy.getKnownMachines().size() == 0) {
            jobFilteringStgy.setKnownMachines(gridInfBroker.getKnownMachines(), gridInfBroker);
        }

        List<UUID> machineSet = jobFilteringStgy.getKnowMachines();

        // Get the job parameters
        String id = job.getName() + job.getReleaseTime().toString() + job.getPriority();

        // CREATE AN ALLOCATION ENTRY
        AllocationEntry entry = null;

        // Función para generar una llave (índice en el hash ring) a partir de un diccionario de atributos
        public int generar_llave(Map<String, Object> atributos) {
            // Concatena los valores de los atributos en una cadena
            StringBuilder atributos_concatenados = new StringBuilder();
            for (Object value : atributos.values()) {
                atributos_concatenados.append(value.toString());
            }
            // Calcula el hash SHA-256 de la cadena concatenada y convierte el resultado en un entero en base 16
            String hash_value = DigestUtils.sha256Hex(atributos_concatenados.toString());
            int hash_value_int = Integer.parseInt(hash_value, 16) % 100;
            // Devuelve el valor de hash
            return hash_value_int;
        }

        // Función para generar una llave para un trabajo utilizando la función generar_llave
        public int generar_llave_trabajo(Map<String, Object> trabajo) {
            return generar_llave(trabajo);
        }

        // Función para generar una llave para una máquina utilizando la función generar_llave
        public int generar_llave_maquina(Map<String, Object> maquina) {
            return generar_llave(maquina);
        }

        // Función para simular un hash ring y asignar trabajos y máquinas a posiciones en el hash ring
        public List<Object[]> simular_hash_ring(List<Map<String, Object>> trabajos, List<Map<String, Object>> maquinas) {
            // Inicializa el hash ring como una lista de 100 elementos null
            List<Object[]> hash_ring = new ArrayList<>(Collections.nCopies(100, null));

            // Asigna trabajos al hash ring utilizando la función generar_llave_trabajo
            for (Map<String, Object> trabajo : trabajos) {
                // Calcula el índice en el hash ring para el trabajo (convierte el valor hexadecimal a entero en base 16)
                int index = generar_llave_trabajo(trabajo);
                // Asigna el trabajo a la posición correspondiente en el hash ring
                hash_ring.set(index, new Object[]{"trabajo", trabajo});
            }

            // Asigna máquinas al hash ring utilizando la función generar_llave_maquina
            for (Map<String, Object> maquina : maquinas) {
                // Calcula el índice en el hash ring para la máquina (convierte el valor hexadecimal a entero en base 16)
                int index = generar_llave_maquina(maquina);
                // Asigna la máquina a la posición correspondiente en el hash ring
                hash_ring.set(index, new Object[]{"maquina", maquina});
            }

            // Devuelve el hash ring con trabajos y máquinas asignados
            return hash_ring;
        }

        // Función para encontrar el nodo responsable para un trabajo en el hash ring
        public Object[] encontrar_nodo_responsable(int index_trabajo, List<Object[]> hash_ring) {
            // Busca el primer nodo (maquina) en el hash_ring que sigue al trabajo
            for (int i = index_trabajo; i < hash_ring.size(); i++) {
                Object[] elemento = hash_ring.get(i);
                if (elemento != null && elemento[0].equals("maquina")) {
                    return (Object[]) elemento[1];
                }
            }

            // Si no se encuentra una maquina en el resto del hash_ring, se busca desde el inicio
            for (int i = 0; i < index_trabajo; i++) {
                Object[] elemento = hash_ring.get(i);
                if (elemento != null && elemento[0].equals("maquina")) {
                    return (Object[]) elemento[1];
                }
            }

            // Si no se encuentra ninguna maquina en el hash_ring, retorna null
            return null;
        }

        // Ejemplo de uso de las funciones
        List<Map<String, Object>> trabajos = new ArrayList<>();
        trabajos.add(Map.of("id", 1, "carga", 50, "descripcion", "Procesar datos"));
        trabajos.add(Map.of("id", 2, "carga", 20, "descripcion", "Analizar datos"));
        trabajos.add(Map.of("id", 3, "carga", 15, "descripcion", "Generar reportes"));
        trabajos.add(Map.of("id", 4, "carga", 30, "descripcion", "Renderizar video"));
        trabajos.add(Map.of("id", 5, "carga", 25, "descripcion", "Entrenar modelo de IA"));
        trabajos.add(Map.of("id", 6, "carga", 40, "descripcion", "Procesar datos"));
        trabajos.add(Map.of("id", 7, "carga", 35, "descripcion", "Analizar datos"));
        trabajos.add(Map.of("id", 8, "carga", 55, "descripcion", "Generar reportes"));
        trabajos.add(Map.of("id", 9, "carga", 45, "descripcion", "Renderizar video"));
        trabajos.add(Map.of("id", 10, "carga", 60, "descripcion", "Entrenar modelo de IA"));

        List<Map<String, Object>> maquinas = new ArrayList<>();
        maquinas.add(Map.of("id", "M1", "capacidad", 100, "descripcion", "Servidor de datos"));
        maquinas.add(Map.of("id", "M2", "capacidad", 80, "descripcion", "Servidor de análisis"));
        maquinas.add(Map.of("id", "M3", "capacidad", 60, "descripcion", "Servidor de reportes"));
        maquinas.add(Map.of("id", "M4", "capacidad", 120, "descripcion", "Servidor de video"));
        maquinas.add(Map.of("id", "M5", "capacidad", 140, "descripcion", "Servidor de IA"));
        maquinas.add(Map.of("id", "M6", "capacidad", 150, "descripcion", "Servidor de datos"));
        maquinas.add(Map.of("id", "M7", "capacidad", 160, "descripcion", "Servidor de análisis"));
        maquinas.add(Map.of("id", "M8", "capacidad", 170, "descripcion", "Servidor de reportes"));
        maquinas.add(Map.of("id", "M9", "capacidad", 180, "descripcion", "Servidor de video"));
        maquinas.add(Map.of("id", "M10", "capacidad", 190, "descripcion", "Servidor de IA"));

        // Crea un hash ring con trabajos y máquinas utilizando la función simular_hash_ring
        List<Object[]> hash_ring = simular_hash_ring(trabajos, maquinas);

        // Recorre el hash ring e imprime el tipo, atributos e índice de cada elemento
        for (int index = 0; index < hash_ring.size(); index++) {
            Object[] elemento = hash_ring.get(index);
            if (elemento != null) {
                String tipo = (String) elemento[0];
                Map<String, Object> atributos = (Map<String, Object>) elemento[1];
                System.out.printf("Index: %d, Tipo: %s, Atributos: %s\n", index, tipo, atributos);

                // Si el elemento es un trabajo, encuentra e imprime el nodo responsable
                if (tipo.equals("trabajo")) {
                    Object[] nodo_responsable = encontrar_nodo_responsable(index, hash_ring);
                    if (nodo_responsable != null) {
                        System.out.printf("Nodo responsable para el trabajo: %s\n", nodo_responsable);
                    } else {
                        System.out.println("No se encontró un nodo responsable para el trabajo");
                    }
                }
            }
        }
        return entry;
    }
}
