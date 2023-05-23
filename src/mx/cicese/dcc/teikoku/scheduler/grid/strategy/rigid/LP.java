package mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid;

import java.util.List;
import java.util.UUID;

import de.irf.it.rmg.core.teikoku.job.Job;
import de.irf.it.rmg.core.teikoku.workload.job.SWFJob;
import mx.cicese.dcc.teikoku.scheduler.plan.AllocationEntry;
import mx.cicese.dcc.teikoku.scheduler.plan.JobControlBlock;

public class LP extends RigidStgy{
	
	/**
	 * Class constructor
	 * 
	*/
		public LP(){
			super();
		}

		
		/**
		 * (non-Javadoc)
		 * 
		 * @see mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid.RigidStgy#schedule(Job job)
		 */
		public AllocationEntry schedule(Job job, JobControlBlock jcb) {
			@Override
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

				const crypto = require('crypto');

				// Función para generar una llave (índice en el hash ring) a partir de un diccionario de atributos
								function generar_llave(atributos) {
								// Concatena los valores de los atributos en una cadena
				  const atributos_concatenados = Object.values(atributos).join('');
								// Calcula el hash SHA-256 de la cadena concatenada y convierte el resultado en un entero en base 16
				  const hash_value = parseInt(crypto.createHash('sha256').update(atributos_concatenados).digest('hex'), 16) % 100;
								// Devuelve el valor de hash
								return hash_value;
				}

				// Función para generar una llave para un trabajo utilizando la función generar_llave
								function generar_llave_trabajo(trabajo) {
								return generar_llave(trabajo);
				}

				// Función para generar una llave para una máquina utilizando la función generar_llave
								function generar_llave_maquina(maquina) {
								return generar_llave(maquina);
				}

				// Función para simular un hash ring y asignar trabajos y máquinas a posiciones en el hash ring
								function simular_hash_ring(trabajos, maquinas) {
									// Inicializa el hash ring como una lista de 100 elementos null
				  const hash_ring = Array(100).fill(null);

									// Asigna trabajos al hash ring utilizando la función generar_llave_trabajo
									for (const trabajo of trabajos) {
										// Calcula el índice en el hash ring para el trabajo (convierte el valor hexadecimal a entero en base 16)
					const index = generar_llave_trabajo(trabajo);
										// Asigna el trabajo a la posición correspondiente en el hash ring
										hash_ring[index] = ['trabajo', trabajo];
									}

									// Asigna máquinas al hash ring utilizando la función generar_llave_maquina
									for (const maquina of maquinas) {
										// Calcula el índice en el hash ring para la máquina (convierte el valor hexadecimal a entero en base 16)
					const index = generar_llave_maquina(maquina);
										// Asigna la máquina a la posición correspondiente en el hash ring
										hash_ring[index] = ['maquina', maquina];
									}

									// Devuelve el hash ring con trabajos y máquinas asignados
									return hash_ring;
								}

				// Función para encontrar el nodo responsable para un trabajo en el hash ring
								function encontrar_nodo_responsable(index_trabajo, hash_ring) {
									// Busca el primer nodo (maquina) en el hash_ring que sigue al trabajo
									for (let i = index_trabajo; i < hash_ring.length; i++) {
										if (hash_ring[i] !== null && hash_ring[i][0] === 'maquina') {
											return hash_ring[i][1];
										}
									}

									// Si no se encuentra una maquina en el resto del hash_ring, se busca desde el inicio
									for (let i = 0; i < index_trabajo; i++) {
										if (hash_ring[i] !== null && hash_ring[i][0] === 'maquina') {
											return hash_ring[i][1];
										}
									}

									// Si no se encuentra ninguna maquina en el hash_ring, retorna null
									return null;
								}

				// Ejemplo de uso de las funciones
				const trabajos = [
								{ id: 1, carga: 50, descripcion: 'Procesar datos' },
								{ id: 2, carga: 20, descripcion: 'Analizar datos' },
								{ id: 3, carga: 15, descripcion: 'Generar reportes' },
								{ id: 4, carga: 30, descripcion: 'Renderizar video' },
								{ id: 5, carga: 25, descripcion: 'Entrenar modelo de IA' },
								{ id: 6, carga: 40, descripcion: 'Procesar datos' },
								{ id: 7, carga: 35, descripcion: 'Analizar datos' },
								{ id: 8, carga: 55, descripcion: 'Generar reportes' },
								{ id: 9, carga: 45, descripcion: 'Renderizar video' },
								{ id: 10, carga: 60, descripcion: 'Entrenar modelo de IA' }
				];

				const maquinas = [
								{ id: 'M1', capacidad: 100, descripcion: 'Servidor de datos' },
								{ id: 'M2', capacidad: 80, descripcion: 'Servidor de análisis' },
								{ id: 'M3', capacidad: 60, descripcion: 'Servidor de reportes' },
								{ id: 'M4', capacidad: 120, descripcion: 'Servidor de video' },
								{ id: 'M5', capacidad: 140, descripcion: 'Servidor de IA' },
								{ id: 'M6', capacidad: 150, descripcion: 'Servidor de datos' },
								{ id: 'M7', capacidad: 160, descripcion: 'Servidor de análisis' },
								{ id: 'M8', capacidad: 170, descripcion: 'Servidor de reportes' },
								{ id: 'M9', capacidad: 180, descripcion: 'Servidor de video' },
								{ id: 'M10', capacidad: 190, descripcion: 'Servidor de IA' }
				];

				// Crea un hash ring con trabajos y máquinas utilizando la función simular_hash_ring
				const hash_ring = simular_hash_ring(trabajos, maquinas);

				// Recorre el hash ring e imprime el tipo, atributos e índice de cada elemento
								for (let index = 0; index < hash_ring.length; index++) {
				  const elemento = hash_ring[index];
									if (elemento !== null) {
					const tipo = elemento[0];
					const atributos = elemento[1];
										console.log(`Index: ${index}, Tipo: ${tipo}, Atributos: ${JSON.stringify(atributos)}`);

										// Si el elemento es un trabajo, encuentra e imprime el nodo responsable
										if (tipo === 'trabajo') {
					  const nodo_responsable = encontrar_nodo_responsable(index, hash_ring);
											if (nodo_responsable !== null) {
												console.log(`nodo responsable para el trabajo: ${JSON.stringify(nodo_responsable)}`);
											} else {
												console.log('No se encontró un nodo responsable para el trabajo');
											}
										}
									}
								}
				return entry;
			}

		} 
}
